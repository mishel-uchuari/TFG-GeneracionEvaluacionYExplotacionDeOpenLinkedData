(ns pipelines.RelacionesPuestoTrabajo (:require [grafter.tabular :refer [_ add-column add-columns apply-columns
                              build-lookup-table column-names columns
                              derive-column drop-rows graph-fn grep make-dataset
                              mapc melt move-first-row-to-header read-dataset
                              read-datasets rows swap swap take-rows
                              test-dataset test-dataset ]]
            [grafter.pipeline :refer [declare-pipeline]]
            [grafter.rdf :as rdf]
            [grafter.rdf.io :as io]
           ; [grafter.rdf :refer [xsd:dateTime]]
            [grafter.vocabularies.qb :refer :all]
            [grafter.rdf.protocols :refer [->Quad]]
            [grafter.rdf.protocols :refer [ITripleWriteable]]
            [grafter.rdf.templater :refer [graph]]
            [grafter.rdf.formats :refer [rdf-nquads rdf-turtle]]
            [grafter.vocabularies.qb :refer :all]
            [grafter.pipeline :refer [declare-pipeline]]
            [grafter.vocabularies.rdf :refer :all]
            [transformaciones.TransformacionesRelacionesPuestoTrabajo :refer :all]      
            [transformaciones.Predicados :refer :all]      
            [transformaciones.TransformacionGeneral :refer :all]
            [clojure.string :as str]
              )
     )
(use 'clojure.java.io)

(def make-graph
 (graph-fn [{:keys [
   FechaMod FechaDescarga CodPuesto Dotacion CodDep CodCentroDest CodCentroO CatRetributiva uriGeneralRPLaborales
   PerfilLinguistico FechaPreceptividad ImporteRetributivo departamento puestoTrabajo centroOrganico centroDestino
    ]
             :as row }]
           ;Nombre de la 
             (graph (base-graph "relaciones-de-puestos-de-trabajo-de-los-departamentos-y-organismos-autonomos-de-la-administracion-de-la-comunidad-autonoma-2017") 
                [uriGeneralRPLaborales
                 [rdf:a employment-contract-predicate]
                 [prefix-fecha-modificacion (dateLabel (row "FechaMod"))]
                 [uriFechaDesc (dateLabel (row "FechaDescarga"))]
                 [uriCodPuesto (row "CodPuesto")]
                 [uriDotacion (row "Dotacion")]
                 [occupation-predicate puestoTrabajo]
                 [uriCodDepto (row "CodDep")]
                 [uriCodCentroDest (row "CodCentroDest")]
                 [uriCodCentroOrg (row "CodCentroO")]
                 [managing-dpt-predicate departamento]
                 [uriCentroOrg centroOrganico]
                 [uriCentroDestino centroDestino]
                 [uriCategoriaRetrib (row "CatRetributiva")]
                 [uriPerfilLing (row "PerfilLinguistico")]
                 [formalized-date-predicate (dateLabel (row "FechaPreceptividad"))]
                 [contract-economic-conditions-predicate (row "ImporteRetributivo")]
                 ]
             ))) 
			 
(defn convert-data-to-data
  [data-file]
  (-> (read-dataset data-file)
    ;Creamos el dataset de los datos a cargar
(make-dataset ["FechaMod" "FechaDescarga" "CodPuesto" "Dotacion" "Puesto"
               "CodDep" "Departamento" "CodCentroO" "CentroOrg" "CodCentroDest"
               "CentroDestino" "CatRetributiva" "PerfilLinguistico"
               "FechaPreceptividad" "ImporteRetributivo"])
    ;Borra la primera fila correspondiente a los nombres de las columnas
(drop-rows 1)
    ;Creamos nuevas columnas donde almacenar el valor en castellano de las columnas
    (derive-column :cDestino [:CentroDestino] removeSymbols)
    (mapc {
        "FechaMod" organizeDate
        "FechaDescarga" organizeDate
        "CodPuesto" parseValue
        "Dotacion" parseValue
        "CodDep" parseValue
        "CodCentroO" bigdec
        "CodCentroDest" parseValue
        "CatRetributiva" parseValue
        "PerfilLinguistico" parseValue
        "FechaPreceptividad" organizeDate
        "ImporteRetributivo" parseValue
          })
  (derive-column :uriGeneralRPLaborales [:Dotacion :cDestino :CodPuesto :FechaMod] uriGeneralRPuestoTrabajo)
  (derive-column :departamento [:Departamento] idiomaEs)
  (derive-column :puestoTrabajo [:Puesto] idiomaEs)
  (derive-column :centroOrganico [:CentroOrg] idiomaEs)
  (derive-column :centroDestino [:CentroDestino] idiomaEs)
   ))

(defn convert-data-to-graph
  [dataset]
  (-> dataset convert-data-to-data make-graph missing-data-filter))

(declare-pipeline convert-data-to-graph [Dataset -> (Seq Statement)]
                  {dataset "The data file to convert into a graph."})

;Convierte una IStatement en una statement Sesame
(defn convertidor [is]
  (map io/IStatement->sesame-statement (convert-data-to-graph is)))