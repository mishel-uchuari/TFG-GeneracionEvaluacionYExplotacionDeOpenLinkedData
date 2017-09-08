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
  Fecha Hora DirMedia Humedad620 Irradia Presion observation-dirMedia observation-humedad620 observation-irradia200 
  observation-presion150 observation-sigDir1100 observation-sigVel1100 observation-temAire610 observation-velMax1100 
  SigDir SigVel TemAire VelMax VelMedia fechaHora uriEstacion
    ] :as row }]
           ;Nombre de la 
             (graph (base-graph "estaciones-meteorologicas-lecturas-recogidas-en-2017") 
                [uriEstacion
                 [rdf:a qb:Observation]
                 [prefix-fecha fechaHora]
                 [prefix-localizacion estacionVitoria]
                 [prefix-medicion uriDirMediaGen]
                 [prefix-medicion uriHumedad620Gen]
                 [prefix-medicion uriIrradia200Gen]
                 [prefix-medicion uriPresion150Gen]
                 [prefix-medicion uriSigDir1100Gen]
                 [prefix-medicion uriSigVel1100Gen]
                 [prefix-medicion uriTemAire610Gen]
                 [prefix-medicion uriVelMax1100Gen]
                 [prefix-medicion uriVelMedia1100Gen]
                 ]
                [observation-dirMedia
                 [rdf:a uriDirMediaGen]
                 [rdfs:comment dirMedia-coment]
                 [prefix-unidad-medida prefix-grados-centigrados]
                 [prefix-valor-observacion (row "DirMedia")]
                   ]
                [observation-humedad620
                 [rdf:a uriHumedad620Gen]
                 [rdfs:comment humedad-coment]
                 [prefix-unidad-medida prefix-porcentaje]
                 [prefix-valor-observacion (row "Humedad620")]
                   ]
                [observation-irradia200
                 [rdf:a uriIrradia200Gen]
                 [rdfs:comment irradia-coment]
                 [prefix-unidad-medida prefix-watios-m2]
                 [prefix-valor-observacion (row "Irradia")]
                   ]
                   [observation-presion150
                 [rdf:a uriPresion150Gen]
                 [rdfs:comment  presion-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "Presion")]
                   ]
                    [observation-sigDir1100
                 [rdf:a uriSigDir1100Gen]
                 [rdfs:comment  sigDir-coment ]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "SigDir")]
                   ]
                     [observation-sigVel1100
                 [rdf:a uriSigVel1100Gen]
                 [rdfs:comment  sigVel-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "SigVel")]
                   ]
                   [observation-temAire610
                 [rdf:a uriTemAire610Gen]
                 [rdfs:comment temAire-coment]
                 [prefix-valor-observacion (row "TemAire")]
                   ]
                    [observation-velMax1100
                 [rdf:a uriVelMax1100Gen]
                 [rdfs:comment  velMax-coment]
                [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "VelMax")]
                   ]
                     [observation-VelMedia1100
                 [rdf:a uriVelMedia1100Gen]
                 [rdfs:comment velMedia-coment]
                 [prefix-unidad-medida prefix-micragramo-mcubico]
                 [prefix-valor-observacion (row "VelMedia")]
                   ] 
             ))) 
			   
(defn convert-data-to-data
  [data-file]
  (-> (read-dataset data-file)
    ;Creamos el dataset de los datos a cargar
(make-dataset ["Fecha" "Hora" "DirMedia" "Humedad620"
               "Irradia" "Presion" "SigDir" "SigVel" "TemAire"
               "VelMax" "VelMedia"])
    ;Borra la primera fila correspondiente a los nombres de las columnas
(drop-rows 1)
    ;Creamos nuevas columnas donde almacenar el valor en castellano de las columnas
     (mapc {
            "Fecha" organizeDate
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
      
 (derive-colum  :fechaHora [:Fecha :Hora] etiquetaFechaHora)
 (derive-column :uriEstacion [:Fecha] prefixEstacion)
 (derive-column :observation-dirMedia [:Fecha :Hora] base-dirMedia)
 (derive-column :observation-humedad620 [:Fecha :Hora] base-humedad620)
 (derive-column :observation-irradia200 [:Fecha :Hora] base-irradia200)
 (derive-column :observation-presion150 [:Fecha :Hora] base-presion150)
 (derive-column :observation-sigDir1100 [:Fecha :Hora] base-sigDir1100)
 (derive-column :observation-sigVel1100 [:Fecha :Hora] base-sigVel1100)
 (derive-column :observation-temAire610 [:Fecha :Hora] base-temAire610)
 (derive-column :observation-velMax1100 [:Fecha :Hora] base-velMax1100)
 (derive-column :observation-VelMedia1100 [:Fecha :Hora] base-velMedia1100)

      ))

(defn convert-data-to-graph
  [dataset]
  (-> dataset convert-data-to-data make-graph missing-data-filter))


(declare-pipeline convert-data-to-graph [Dataset -> (Seq Statement)]
                  {dataset "The data file to convert into a graph."})

;Convierte una IStatement en una statement Sesame
(defn convertidor [is]
  (map io/IStatement->sesame-statement (convert-data-to-graph is)))

