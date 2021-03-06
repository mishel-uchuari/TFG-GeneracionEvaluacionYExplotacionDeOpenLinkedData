(ns pipelines.EstacionesMetereologicas

  (:require [grafter.tabular :refer [_ add-column add-columns apply-columns
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
            [transformaciones.Predicados :refer :all]
            [transformaciones.TransformacionGeneral :refer :all]
            [transformaciones.TransformacionesEstacionesMetereologicas :refer :all]
            [clojure.string :as str]
              )
     )

;Construye el grafo a partir de las tripletas que se especifican
(def make-graph
 (graph-fn [{:keys [
  Fecha Hora DirMedia Humedad620 Irradia Presion observation-dirMedia observation-humedad620 observation-irradia200 
  observation-presion150 observation-sigDir1100 observation-sigVel1100 observation-temAire610 observation-velMax1100 
  SigDir SigVel TemAire VelMax VelMedia 
  dateHour stationUri observation-VelMedia1100
    ] :as row }]
           ;Nombre del grafo
             (graph (graph-base "estaciones-metereologicas-en-2017") 
                [stationUri
                 [rdf:a qb:Observation]
                 [rdfs:label (languageSpanish(str "Observacion estacion c040"))]
                 [rdfs:comment observationc040-coment]
                 [date-predicate dateHour]
                 [location-predicate vitoria-station]
                 [resource-measurement-base observation-dirMedia]
                 [resource-measurement-base observation-humedad620]
                 [resource-measurement-base observation-irradia200]
                 [resource-measurement-base observation-presion150]
                 [resource-measurement-base observation-sigDir1100]
                 [resource-measurement-base observation-sigVel1100]
                 [resource-measurement-base observation-temAire610]
                 [resource-measurement-base observation-velMax1100]
                 [resource-measurement-base observation-VelMedia1100]
                 ]
                ;Observacion direccion media
                [observation-dirMedia
                 [rdf:a uriDirMediaGen]
                 [rdfs:label (languageSpanish(str "dirMedia"))]
                 [rdfs:comment dirMedia-coment]
                 [unit-measure-predicate degrees-celsius-predicate]
                 [observation-value-predicate (row "DirMedia")]
                   ]
                ;Observacion humedad 620
                [observation-humedad620
                 [rdf:a uriHumedad620Gen]
                 [rdfs:label (languageSpanish(str "Humedad 620"))]
                 [rdfs:comment humedad-coment]
                 [unit-measure-predicate percentage-predicate]
                 [observation-value-predicate (row "Humedad620")]
                   ]
                ;Observacion irradia 200
                [observation-irradia200
                 [rdf:a uriIrradia200Gen]
                 [rdfs:comment irradia-coment]
                 [rdfs:label (languageSpanish(str "Irradia 200"))]
                 [unit-measure-predicate watios-m2-predicate]
                 [observation-value-predicate (row "Irradia")]
                   ]
                ;Observacion Presion 150
                   [observation-presion150
                 [rdf:a uriPresion150Gen]
                 [rdfs:label (languageSpanish(str "Presion 150"))]
                 [rdfs:comment  presion-coment]
                 [unit-measure-predicate milibar-predicate]
                 [observation-value-predicate (row "Presion")]
                   ]
                   ;Observacion Sigdir 1100
                    [observation-sigDir1100
                 [rdf:a uriSigDir1100Gen]
                 [rdfs:label (languageSpanish(str "Sig Dir 1100"))]
                 [rdfs:comment  sigDir-coment]
                 [unit-measure-predicate degrees-celsius-predicate]
                 [observation-value-predicate (row "SigDir")]
                   ]
                    ;Observacion Sigvel 1100
                     [observation-sigVel1100
                 [rdf:a uriSigVel1100Gen]
                 [rdfs:label (languageSpanish(str "Sig Vel 1100"))]
                 [rdfs:comment  sigVel-coment]
                 [unit-measure-predicate km-hour-predicate]
                 [observation-value-predicate (row "SigVel")]
                   ]
                     ;Observacion Temperatura aire 610
                   [observation-temAire610
                 [rdf:a uriTemAire610Gen]
                 [rdfs:label (languageSpanish(str "Temp Aire 610"))]
                 [rdfs:comment temAire-coment]
                 [unit-measure-predicate degrees-celsius-predicate]
                 [observation-value-predicate (row "TemAire")]
                   ]
                   ;Observacion VelMax 1100
                    [observation-velMax1100
                 [rdf:a uriVelMax1100Gen]
                 [rdfs:label (languageSpanish(str "Vel Max 1100"))]
                 [rdfs:comment  velMax-coment]
                 [unit-measure-predicate km-hour-predicate]
                 [observation-value-predicate (row "VelMax")]
                   ]
                    ;Observacion Vel Media 1100
                     [observation-VelMedia1100
                 [rdf:a uriVelMedia1100Gen]
                 [rdfs:label (languageSpanish(str "Vel Media 1100"))]
                 [rdfs:comment velMedia-coment]
                 [unit-measure-predicate km-hour-predicate]
                 [observation-value-predicate (row "VelMedia")]
                   ] 
             ))) 
;Convierte los datos del csv a datos clojure y aplica funciones sobre ellos
(defn read-csv-data
  [data-file]
  (-> (read-dataset data-file)
    ;Creamos el dataset de los datos a cargar
(make-dataset [
               "Fecha" "Hora" "DirMedia" "Humedad620"
               "Irradia" "Presion" "SigDir" "SigVel" "TemAire"
               "VelMax" "VelMedia"
               ])
    ;Borra la primera fila correspondiente a los nombres de las columnas
(drop-rows 1)
     (derive-column :hour [:Hora] removeSymbols)
     (mapc {
            "Fecha" organizeDateUSA
            "DirMedia" parseValue
            "Humedad620" parseValue
            "Irradia" parseValue
            "Presion" parseValue
            "SigDir" parseValue
            "SigVel" parseValue
            "TemAire" parseValue
            "VelMax" parseValue
            "VelMedia" parseValue
          })
 (derive-column :dateHour [:Fecha :Hora] dateHourLabel)
 (derive-column :stationUri [:Fecha] station-uri)
 (derive-column :observation-dirMedia [:Fecha :hour] base-dirMedia)
 (derive-column :observation-humedad620 [:Fecha :hour] base-humedad620)
 (derive-column :observation-irradia200 [:Fecha :hour] base-irradia200)
 (derive-column :observation-presion150 [:Fecha :hour] base-presion150)
 (derive-column :observation-sigDir1100 [:Fecha :hour] base-sigDir1100)
 (derive-column :observation-sigVel1100 [:Fecha :hour] base-sigVel1100)
 (derive-column :observation-temAire610 [:Fecha :hour] base-temAire610)
 (derive-column :observation-velMax1100 [:Fecha :hour] base-velMax1100)
 (derive-column :observation-VelMedia1100 [:Fecha :hour] base-velMedia1100)
)
  )

(defn convert-data-to-graph
  [dataset]
  (-> dataset read-csv-data make-graph missing-data-filter))

;Se declara el pipeline
(declare-pipeline convert-data-to-graph [Dataset -> (Seq Statement)]
                  {dataset "The data file to convert into a graph."})

;Convierte una IStatement en una statement Sesame
(defn create-graph [is]
  (map io/IStatement->sesame-statement (convert-data-to-graph is)))