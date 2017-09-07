(ns pipelines.RetribucionesNominativas  (:require [grafter.tabular :refer [_ add-column add-columns apply-columns
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
            [transformaciones.TransformacionesRetribucionesNominativas :refer :all]      
            [transformaciones.Predicados :refer :all]      
            [transformaciones.TransformacionGeneral :refer :all]
            [clojure.string :as str]
              )
     )
(use 'clojure.java.io)

(def make-graph
 (graph-fn [{:keys [
NomAp CargoPublico Retribucion FechaInicio FechaFin IdDpto uriDepartamento uriGralRNominativas
Departamento IdOrgano Organo IdCentro CentroOrganico FechaActualizado uriEmpleado 
    ] :as row }]
           ;Nombre de la 
             (graph (base-graph "estaciones-meteorologicas-lecturas-recogidas-en-2017") 
                [uriGralRNominativas
                 [rdf:a prefix-contrato-trabajo]
                 [prefix-empleado uriEmpleado]
                 [prefix-puesto (idiomaEs (str (removeSymbols (row "CargoPublico"))))]
                 [prefix-condiciones-economicas (parseValue (row "Retribucion"))]
                 [prefix-fecha-formalizacion (etiquetaFecha (row "FechaInicio"))]
                 [prefix-fecha-finalizacion (etiquetaFecha (row "FechaFin"))]
                 [uriIdDpto (row "IdDpto")]
                 [prefix-departamento-gerente uriDepartamento]
                 [uriOrgano (idiomaEs (str (removeSymbols (row "Organo"))))]
                 [uriIdCentroOrganico (row "IdCentro")]
                 [uriCentroOrganico (idiomaEs (str (removeSymbols (row "CentroOrganico"))))]
                 [prefix-fecha-modificacion (etiquetaFecha(row "FechaActualizado"))]
                 ]
             ))) 
			   
(defn convert-data-to-data
  [data-file]
  (-> (read-dataset data-file)
    ;Creamos el dataset de los datos a cargar
(make-dataset ["NomAp" "CargoPublico" "Retribucion" "FechaInicio" "FechaFin" "IdDpto"
              "Departamento" "IdOrgano" "Organo" "IdCentro" "CentroOrganico" "FechaActualizado"])
    ;Borra la primera fila correspondiente a los nombres de las columnas
(drop-rows 1)
      (mapc {"FechaInicio" organizeDate
            "FechaFin" organizeDate
            "FechaActualizado" organizeDate
            "NomAp" removeSymbols
            "CargoPublico" removeSymbols
            "Retribucion" removeSymbols
            "IdDpto" parseValue
            "Departamento" removeSymbols
            "IdOrgano" parseValue
            "Organo" removeSymbols
            "IdCentro" parseValue
          })
  (derive-column  :uriGralRNominativas [:CargoPublico :NomAp :Departamento :Organo :FechaActualizado] uriGralRNominativas)
  (derive-column :uriEmpleado [:NomAp] uriGralEmpleado)
  (derive-column :uriDepartamento [:Departamento] uriGralDpto)
 ))

(defn convert-data-to-graph
  [dataset]
  (-> dataset convert-data-to-data make-graph missing-data-filter))

(declare-pipeline convert-data-to-graph [Dataset -> (Seq Statement)]
                  {dataset "The data file to convert into a graph."})

;Convierte una IStatement en una statement Sesame
(defn convertidor [is]
  (map io/IStatement->sesame-statement (convert-data-to-graph is)))