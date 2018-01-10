(ns pipelines.CalidadDelAire
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
            [transformaciones.TransformacionesCalidadDelAire :refer :all]      
            [transformaciones.Predicados :refer :all]      
            [transformaciones.TransformacionGeneral :refer :all]
            [clojure.string :as str]
              )
     )

;Construye el grafo a partir de las tripletas que se especifican
(def make-graph
 (graph-fn [{:keys [
   ;Datos observaciones
    observation-CO observation-NO2max observation-CO8hmax
    observation-Benceno observation-CO8hmax observation-C8hAQ 
    observation-C8hAQ observation-Etilbenceno observation-NO
    observation-NO2 observation-NO2AQ observation-NOX 
    observation-Ortoxileno observation-PM10 observation-PM10AQ 
    observation-PM25 observation-PM25AQ observation-Tolueno
    observation-ICAAQ Date dateValue
     ;Datos String
    CO-8h-Air-Quality varCO8AQ-CAST ;Version Castellano-Euskera
    NO2-Air-Quality  varNO2AQ-CAST  ;Version Castellano-Euskera
    PM10-Air-Quality varPM10AQ-CAST ;Version Castellano-Euskera
    PM25-Air-Quality varPM25AQ-CAST ;Version Castellano-Euskera
    ICA-estacion varICAE-CAST ;Version Castellano-Euskera
    ;Datos numéricos
    Benceno CO CO-8hmax NO NO2 NOX Ortoxileno                
    PM10 PM25 Tolueno NO2max Etilbenceno uriEstacion
    ]
             :as row }]
           ;Nombre del grafo
             (graph (graph-base "calidad-aire-en-euskadi-2017") 
                [uriEstacion
                 [rdf:a qb:Observation]
                 [rdfs:label observationAvG-coment]
                 [date-predicate dateValue]
                 [location-predicate vitoria-station]
                 [resource-measurement-base uriCOgen]
                 [resource-measurement-base uriCO8hAirQualitygen]
                 [resource-measurement-base uriCO8hmaxgen]
                 [resource-measurement-base uriNOgen]
                 [resource-measurement-base uriNO2gen]
                 [resource-measurement-base uriNO2maxgen]
                 [resource-measurement-base uriNO2AirQualitygen]
                 [resource-measurement-base uriNOXgen]
                 [resource-measurement-base uriPM10gen]
                 [resource-measurement-base uriPM10AirQualitygen]
                 [resource-measurement-base uriPM25gen]
                 [resource-measurement-base uriPM25AirQualitygen]
                 [resource-measurement-base uriICAEstaciongen]
                 [resource-measurement-base uriOrtoxilenogen]
                 [resource-measurement-base uriEtilbencenogen]
                 [resource-measurement-base uriToluenogen]
                 [resource-measurement-base uriBencenogen]
                 ]          
                [observation-CO
                 [rdf:a uriCOgen]
                 [rdfs:label CO-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate milligram-cmeter-predicate]
                 [observation-value-predicate (row "CO") ]
                   ]
                [observation-CO8hmax
                 [rdf:a uriCO8hmaxgen]
                 [rdfs:label CO8hmax-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate milligram-cmeter-predicate]
                 [observation-value-predicate (row "CO-8hmax")]
                   ]
                [observation-C8hAQ
                 [rdf:a uriCO8hAirQualitygen]
                 [rdfs:label CO8hAQ-coment]
                 [predicate-medition-associated uriEstacion]
                 [observation-value-predicate (languageVasque (str (row "CO-8h-Air-Quality")))]
                 [observation-value-predicate (languageSpanish (str varCO8AQ-CAST))]
                   ]
                   [observation-NO
                 [rdf:a uriNOgen]
                 [rdfs:label  NO-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "NO")]
                   ]
                    [observation-NO2
                 [rdf:a uriNO2gen]
                 [rdfs:label  NO2-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "NO2")]
                   ]
                     [observation-NO2max
                 [rdf:a uriNO2maxgen]
                 [rdfs:label  NO2max-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "NO2max")]
                   ]
                   [observation-NO2AQ
                 [rdf:a uriNO2AirQualitygen]
                 [rdfs:label NO2AQ-coment]
                 [predicate-medition-associated uriEstacion]
                 [observation-value-predicate (languageVasque (str (row "NO2-Air-Quality")))]
                 [observation-value-predicate (languageSpanish (str varNO2AQ-CAST))]
                   ]
                    [observation-NOX
                 [rdf:a uriNOXgen]
                 [rdfs:label  NOX-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "NOX")]
                   ]
                     [observation-PM10
                 [rdf:a uriPM10gen]
                 [rdfs:label PM10-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "PM10")]
                   ] 
                 [observation-PM10AQ 
                 [rdf:a uriPM10AirQualitygen]
                 [rdfs:label PM10AQ-coment]
                 [predicate-medition-associated uriEstacion]
                 [observation-value-predicate (languageVasque (str (row "PM10-Air-Quality")))]
                 [observation-value-predicate (languageSpanish (str varPM10AQ-CAST ))]
                   ] 
                   [observation-PM25
                 [rdf:a uriPM25gen]
                 [rdfs:label PM25-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "PM25")]
                   ]  
                    [observation-PM25AQ 
                 [rdf:a uriPM25AirQualitygen]
                 [rdfs:label PM25AQ-coment]
                 [predicate-medition-associated uriEstacion]
                 [observation-value-predicate (languageVasque (str (row "PM25-Air-Quality")))]
                 [observation-value-predicate (languageSpanish (str varPM25AQ-CAST))]
                   ] 
                   [observation-ICAAQ
                 [rdf:a uriICAEstaciongen]
                 [rdfs:label ICAAQ-coment]
                 [predicate-medition-associated uriEstacion]
                 [observation-value-predicate (languageVasque (str (row "ICA-estacion")))]
                 [observation-value-predicate (languageSpanish (str varICAE-CAST))]
                   ] 
                    [observation-Benceno
                 [rdf:a uriBencenogen]
                 [rdfs:label Benceno-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "Benceno")]
                   ]
                    [observation-Tolueno
                 [rdf:a uriToluenogen]
                 [rdfs:label Tolueno-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "Tolueno")]
                   ]
                    [observation-Ortoxileno
                 [rdf:a uriOrtoxilenogen]
                 [rdfs:label Ortoxileno-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "Ortoxileno")]
                   ]
                   [observation-Etilbenceno
                 [rdf:a uriEtilbencenogen]
                 [rdfs:label Etilbenceno-coment]
                 [predicate-medition-associated uriEstacion]
                 [unit-measure-predicate micragram-cmeter-predicate]
                 [observation-value-predicate (row "Etilbenceno")]
                   ]
             ))) 

;Convierte los datos del csv a datos clojure y aplica funciones sobre ellos
(defn convert-data-to-data
  [data-file]
  (-> (read-dataset data-file)
    ;Creamos el dataset de los datos a cargar
 (make-dataset ["Date" "Benceno" "CO" "CO-8hmax" "CO-8h-Air-Quality"
                "Etilbenceno" "NO" "NO2" "NO2max" 
                "NO2-Air-Quality" "NOX" "Ortoxileno"
                "PM10" "PM10-Air-Quality" "PM25"
                "PM25-Air-Quality" "Tolueno" "ICA-estacion"])
    ;Borra la primera fila correspondiente a los nombres de las columnas
 (drop-rows 1)
    ;Creamos nuevas columnas donde almacenar el valor en castellano de las columnas
 (derive-column :varCO8AQ-CAST "CO-8h-Air-Quality")
 (derive-column :varNO2AQ-CAST "NO2-Air-Quality")
 (derive-column :varPM10AQ-CAST "PM10-Air-Quality")
 (derive-column :varPM25AQ-CAST "PM25-Air-Quality")
 (derive-column :varICAE-CAST "ICA-estacion")
 
      (mapc {"Date" organizeDate
          "Benceno" parseValue
          "CO" parseValue
          "CO-8hmax" parseValue
          ;Version euskera
          "CO-8h-Air-Quality" makeSplitEusk
          ;Version castellano
          :varCO8AQ-CAST makeSplitCast
          "Etilbenceno" parseValue
          "NO" parseValue
          "NO2" parseValue
          "NO2max" parseValue
          ;Version euskera 
          "NO2-Air-Quality" makeSplitEusk
          ;Version castellano
          :varNO2AQ-CAST makeSplitCast
          "NOX" parseValue
          "Ortoxileno" parseValue
          "PM10" parseValue
          ;Version euskera
          "PM10-Air-Quality" makeSplitEusk
          ;Version castellano
          :varPM10AQ-CAST makeSplitCast
          "PM25" parseValue
          ;Version euskera
          "PM25-Air-Quality" makeSplitEusk
          ;Version castellano
          :varPM25AQ-CAST makeSplitCast
          "Tolueno" parseValue
          ;Version euskera
          "ICA-estacion" makeSplitEusk  ;
          ;Version castellano
          :varICAE-CAST makeSplitCast
          })
  (derive-column :uriEstacion [:Date] station-uri)
  (derive-column :dateValue [:Date] dateLabel)
  (derive-column :observation-CO [:Date] base-CO)
  (derive-column :observation-CO8hmax [:Date] base-CO8hmax)
  (derive-column :observation-C8hAQ [:Date] base-CO8hAQ)
  (derive-column :observation-NO [:Date] base-NO)
  (derive-column :observation-NO2 [:Date] base-NO2)
  (derive-column :observation-NO2max [:Date] base-NO2max)
  (derive-column :observation-Etilbenceno [:Date] base-Etilbenceno)
  (derive-column :observation-NO2AQ [:Date] base-NO2AQ)
  (derive-column :observation-NOX [:Date] base-NOX)
  (derive-column :observation-PM10 [:Date] base-PM10)
  (derive-column :observation-PM10AQ [:Date] base-PM10AQ)
  (derive-column :observation-PM25 [:Date] base-PM25)
  (derive-column :observation-PM25AQ [:Date] base-PM25AQ)
  (derive-column :observation-ICAAQ [:Date] base-ICAAQ)
  (derive-column :observation-Tolueno [:Date] base-Tolueno)
  (derive-column :observation-Ortoxileno [:Date] base-Ortoxileno)
  (derive-column :observation-Benceno [:Date] base-Benceno)
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