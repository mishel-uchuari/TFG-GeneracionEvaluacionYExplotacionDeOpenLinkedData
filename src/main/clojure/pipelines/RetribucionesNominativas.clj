(ns pipelines.RetribucionesNominativas  (:require [grafter.tabular :refer [_ add-column add-columns apply-columns
                              build-lookup-table column-names columns
                              derive-column drop-rows graph-fn grep make-dataset
                              mapc melt move-first-row-to-header read-dataset
                              read-datasets rows swap swap take-rows
                              test-dataset test-dataset ]]
            [grafter.pipeline :refer [declare-pipeline]]
            [grafter.rdf :as rdf]
            [grafter.rdf.io :as io]
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
;Construye el grafo a partir de las tripletas que se especifican
(def make-graph
 (graph-fn [{:keys [
NomAp CargoPublico FechaInicio FechaFin IdDpto uriGralRNominativas Retribucion
Departamento IdOrgano Organo IdCentro CentroOrganico FechaActualizado employeeUri 
departmentUri  retribucionSinEspacios nomApUri
    ] :as row }]
           ;Nombre del grafo
             (graph (graph-base "retribuciones-nominativas-2017") 
                [uriGralRNominativas
                 [rdf:a employment-contract-predicate]
                 [rdfs:label retribucionesNom-coment]
                 [employee-predicate employeeUri]
                 [occupation-predicate (languageSpanish (str (row "CargoPublico")))]
                 [formalized-date-predicate (dateLabel (row "FechaInicio"))]
                 [ending-date-predicate (dateLabel (row "FechaFin"))]
                 [dpto-id-predicate (row "IdDpto")]
                 [managing-dpt-predicate departmentUri]
                 [organ-predicate (languageSpanish (str (row "Organo")))]
                 [organic-center-id-predicate (row "IdCentro")]
                 [organic-center-predicate (languageSpanish (str (row "CentroOrganico")))]
                 [modified-date-predicate (dateLabel (row "FechaActualizado"))]
                 [contract-economic-conditions-predicate (parseValue (row "Retribucion"))]
                 ]
                [employeeUri 
                 [rdf:a person-predicate]
                 [rdfs:label (languageSpanish (row "NomAp"))]
                 [rdfs:label (languageVasque (row "NomAp"))]
                 ]
                             ))) 
;Convierte los datos del csv a datos clojure y aplica funciones sobre ellos
(defn convert-data-to-data
  [data-file]
  (-> (read-dataset data-file)
    ;Creamos el dataset de los datos a cargar
(make-dataset ["NomAp" "CargoPublico" "Retribucion" "FechaInicio" "FechaFin" "IdDpto"
              "Departamento" "IdOrgano" "Organo" "IdCentro" "CentroOrganico" "FechaActualizado"])
    ;Borra la primera fila correspondiente a los nombres de las columnas
(drop-rows 1)
  (derive-column  :nomApUri "NomAp")
  (derive-column  :retribucionSinEspacios "Retribucion")
  (derive-column  :cargoPublicoSinEspacios "CargoPublico")
  (derive-column  :organoSinEspacios "Organo")
  (derive-column  :departamentoSinEspacios "Departamento")

      (mapc {"FechaInicio" organizeDate
            "FechaFin" organizeDate
            "FechaActualizado" organizeDate
            "nomApUri" removeSymbols
            "IdDpto" parseValue
            "Retribucion" removeBlanks
            "IdOrgano" parseValue
            "IdCentro" parseValue
          }) 
  (derive-column  :uriGralRNominativas [:cargoPublicoSinEspacios :nomApUri :departamentoSinEspacios :organoSinEspacios :FechaActualizado] uriGralRNominativas)
  (derive-column :employeeUri [:nomApUri] uriGralEmployee)
  (derive-column :departmentUri [:departamentoSinEspacios] uriGralDpto)
 ))

(defn convert-data-to-graph
  [dataset]
  (-> dataset convert-data-to-data make-graph missing-data-filter))
;Se declara el pipeline
(declare-pipeline convert-data-to-graph [Dataset -> (Seq Statement)]
                  {dataset "The data file to convert into a graph."})

;Convierte una IStatement en una statement Sesame
(defn convertidor [is]
  (map io/IStatement->sesame-statement (convert-data-to-graph is)))
