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
           ; [grafter.rdf :refer [xsd:dateTime]]
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
            [clojure.string :as str]
              )
     )


(def make-graph
 (graph-fn [{:keys [
  Fecha Hora DirMedia Humedad620 Irradia Presion
  SigDir SigVelTemAire VelMax VelMedia fechaHora
    ] :as row }]
           ;Nombre de la 
             (graph (base-graph "estaciones-meteorologicas-lecturas-recogidas-en-2017") 
                [uriEstacion
                 [rdf:a qb:Observation]
                 [prefix-fecha dateValue]
                 [prefix-localizacion estacionVitoria]
                 [prefix-medicion uriCOgen]
                 [prefix-medicion uriCO8hAirQualitygen]
                 [prefix-medicion uriCO8hmaxgen]
                 [prefix-medicion uriNOgen]
                 [prefix-medicion uriNO2gen]
                 [prefix-medicion uriNO2maxgen]
                 [prefix-medicion uriNO2AirQualitygen]
                 [prefix-medicion uriNOXgen]
                 [prefix-medicion uriPM10gen]
                 [prefix-medicion uriPM10AirQualitygen]
                 [prefix-medicion uriPM25gen]
                 [prefix-medicion uriPM25AirQualitygen]
                 [prefix-medicion uriICAEstaciongen]
                 [prefix-medicion uriOrtoxilenogen]
                 [prefix-medicion uriEtilbencenogen]
                 [prefix-medicion uriToluenogen]
                 [prefix-medicion uriBencenogen]
                 ]          
                [observation-CO
                 [rdf:a uriCOgen]
                 [rdfs:comment CO-coment]
                 [prefix-unidad-medida prefix-miligramo-mcubico]
                 [prefix-valor-observacion (row "CO") ]
                   ]
                [observation-CO8hmax
                 [rdf:a uriCO8hmaxgen]
                 [rdfs:comment CO8hmax-coment]
                 [prefix-unidad-medida prefix-miligramo-mcubico]
                 [prefix-valor-observacion (row "CO-8hmax")]
                   ]
                [observation-C8hAQ
                 [rdf:a uriCO8hAirQualitygen]
                 [rdfs:comment CO8hAQ-coment]
                 [prefix-valor-observacion (idiomaEusk (str (removeSymbols (row "CO-8h-Air-Quality"))))]
                 [prefix-valor-observacion (idiomaEs (str (removeSymbols varCO8AQ-CAST)))]
                   ]
                   [observation-NO
                 [rdf:a uriNOgen]
                 [rdfs:comment  NO-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "NO")]
                   ]
                    [observation-NO2
                 [rdf:a uriNO2gen]
                 [rdfs:comment  NO2-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "NO2")]
                   ]
                     [observation-NO2max
                 [rdf:a uriNO2maxgen]
                 [rdfs:comment  NO2max-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "NO2max")]
                   ]
                   [observation-NO2AQ
                 [rdf:a uriNO2AirQualitygen]
                 [rdfs:comment NO2AQ-coment]
                 [prefix-valor-observacion (idiomaEusk (str (removeSymbols (row "NO2-Air-Quality"))))]
                 [prefix-valor-observacion (idiomaEs (str (removeSymbols varNO2AQ-CAST)))]
                   ]
                    [observation-NOX
                 [rdf:a uriNOXgen]
                 [rdfs:comment  NOX-coment]
                [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "NOX")]
                   ]
                     [observation-PM10
                 [rdf:a uriPM10gen]
                 [rdfs:comment PM10-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "PM10")]
                   ] 
                 [observation-PM10AQ 
                 [rdf:a uriPM10AirQualitygen]
                 [rdfs:comment PM10AQ-coment]
                 [prefix-valor-observacion (idiomaEusk (str (removeSymbols (row "PM10-Air-Quality"))))]
                 [prefix-valor-observacion (idiomaEs (str (removeSymbols varPM10AQ-CAST )))]
                   ] 
                   [observation-PM25
                 [rdf:a uriPM25gen]
                 [rdfs:comment PM25-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "PM25")]
                   ]  
                    [observation-PM25AQ 
                 [rdf:a uriPM25AirQualitygen]
                 [rdfs:comment PM25AQ-coment]
                 [prefix-valor-observacion (idiomaEusk (str (removeSymbols (row "PM25-Air-Quality"))))]
                 [prefix-valor-observacion (idiomaEs (str (removeSymbols varPM25AQ-CAST )))]
                   ] 
                   [observation-ICAAQ
                 [rdf:a uriICAEstaciongen]
                 [rdfs:comment ICAAQ-coment]
                 [prefix-valor-observacion (idiomaEusk (str (removeSymbols (row "ICA-estacion"))))]
                 [prefix-valor-observacion (idiomaEs (str (removeSymbols varICAE-CAST)))]
                   ] 
                    [observation-Benceno
                 [rdf:a uriBencenogen]
                 [rdfs:comment Benceno-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "Benceno")]
                   ]
                    [observation-Tolueno
                 [rdf:a uriToluenogen]
                 [rdfs:comment Tolueno-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "Tolueno")]
                   ]
                    [observation-Ortoxileno
                 [rdf:a uriOrtoxilenogen]
                 [rdfs:comment Ortoxileno-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "Ortoxileno")]
                   ]
                   [observation-Etilbenceno
                 [rdf:a uriEtilbencenogen]
                 [rdfs:comment Etilbenceno-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "Etilbenceno")]
                   ]   
             ))) 
			   
(defn convert-data-to-data
  [data-file]
  (-> (read-dataset data-file)
    ;Creamos el dataset de los datos a cargar
 (make-dataset ["IdDia" "Fecha" "Hora" "DirMedia" "Humedad620"
                "Irradia" "Presion" "SigDir" "SigVel" "TemAire"
                "VelMax" "VelMedia"])
    ;Borra la primera fila correspondiente a los nombres de las columnas
 (drop-rows 1)
    ;Creamos nuevas columnas donde almacenar el valor en castellano de las columnas
      (mapc {"Fecha" organizeDate
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
  (derive-colum  :fechaHora [:Fecha :Hora] fechaHora)
  (derive-column :uriEstacion [:Date] prefixEstacion)
      ))


(defn convert-data-to-graph
  [dataset]
  (-> dataset convert-data-to-data make-graph missing-data-filter))


(declare-pipeline convert-data-to-graph [Dataset -> (Seq Statement)]
                  {dataset "The data file to convert into a graph."})

;Convierte una IStatement en una statement Sesame
(defn convertidor [is]
  (map io/IStatement->sesame-statement (convert-data-to-graph is)))

