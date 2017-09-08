(ns transformaciones.TransformacionesEstacionesMetereologicas)

(def dirMedia "direccionMedia1100")
(def humedad620 "humedad620")
(def irradia200 "irradia200")
(def presion150 "presion150")
(def sigDir1100 "sigDir1100")
(def sigVel1100 "sigVel1100")
(def temAire610 "temAire610")
(def velMax1100 "velMax1100")
(def velMedia1100 "velMedia1100")

(def uriDirMediaGen (base-medicion-pred dirMedia)) 
(def uriHumedad620Gen (base-medicion-pred humedad620)) 
(def uriIrradia200Gen (base-medicion-pred irradia200)) 
(def uriPresion150Gen (base-medicion-pred presion150)) 
(def uriSigDir1100Gen (base-medicion-pred sigDir1100)) 
(def uriSigVel1100Gen (base-medicion-pred sigVel1100)) 
(def uriTemAire610Gen (base-medicion-pred temAire610)) 
(def uriVelMax1100Gen (base-medicion-pred velMax1100)) 
(def uriVelMedia1100Gen (base-medicion-pred velMedia1100)) 

(def dirMedia-coment (idiomaEs (str "Dirección o procedencia media del viento expresada en grados")))
(def sigDir-coment (idiomaEs (str "Sigma de la dirección del viento expresada en grados")))
(def humedad-coment (idiomaEs (str "Humedad relativa del aire expresada en porcentaje")))
(def irradia-coment (idiomaEs (str "Irradiancia global sobre una superficie plana expresada en watios por metro2")))
(def presion-coment (idiomaEs (str "Presión atmosférica expresada en milibares")))
(def sigVel-coment (idiomaEs (str "Sigma de la velocidad del viento expresada en kilómetros por hora")))
(def temAire-coment (idiomaEs (str "Temperatura del aire expresada en grados centígrados")))
(def velMax-coment (idiomaEs (str "Racha máxima del viento horizontal expresada en kilómetros por hora")))
(def velMedia-coment (idiomaEs (str "Velocidad media del viento expresada en kilómetros por hora")))

;Uri estacion
(defn prefixEstacion[pFecha]
  (base-estacion
    (str "-c040-" (formatoFecha pFecha))))

;Uris predicados
(defn base-dirMedia[pFecha] 
  (base-medicion-pred
    (str pFecha "/" dirMedia )))

(defn base-humedad620[pFecha] 
  (base-medicion-pred
    (str pFecha "/" humedad620 )))

(defn base-irradia200[pFecha] 
  (base-medicion-pred
    (str pFecha "/" irradia200 )))

(defn base-presion150[pFecha] 
  (base-medicion-pred
    (str pFecha "/" presion150 )))

(defn base-sigDir1100[pFecha] 
  (base-medicion-pred
    (str pFecha "/" sigDir1100 )))

(defn base-sigVel1100[pFecha] 
  (base-medicion-pred
    (str pFecha "/" sigVel1100 )))

(defn base-temAire610[pFecha]
  (base-medicion-pred
    (str pFecha "/" temAire610)))

(defn base-velMax1100[pFecha] 
  (base-medicion-pred
    (str pFecha "/" velMax1100 )))

(defn base-velMedia1100[pFecha] 
  (base-medicion-pred
    (str pFecha "/" velMedia1100 )))

;Uris recursos
(defn uri-recurso-dirMedia[pFecha pHora] 
  (base-elemento
    (str pFecha "-" pHora "-" dirMedia )))

(defn uri-recurso-velMax1100[pFecha pHora] 
  (base-elemento
    (str pFecha "-" pHora "-" velMax1100 )))

(defn uri-recurso-humedad620[pFecha pHora] 
  (base-elemento
    (str pFecha "-" pHora "-" humedad620 )))

(defn uri-recurso-irradia200[pFecha pHora] 
  (base-elemento
    (str pFecha "-" pHora "-" irradia200 )))

(defn uri-recurso-presion150[pFecha pHora] 
  (base-elemento
    (str pFecha "-" pHora "-" presion150 )))

(defn uri-recurso-sigDir1100[pFecha pHora] 
  (base-elemento
    (str pFecha "-" pHora "-" sigDir1100 )))

(defn uri-recurso-sigVel1100[pFecha pHora] 
  (base-elemento
    (str pFecha "-" pHora "-" sigVel1100 )))

(defn uri-recurso-temAire610[pFecha pHora] 
  (base-elemento
    (str pFecha "-" pHora "-" temAire610 )))

