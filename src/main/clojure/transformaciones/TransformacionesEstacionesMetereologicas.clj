(ns transformaciones.TransformacionesEstacionesMetereologicas (:require 
    [clojure.string :as str]
    [grafter.rdf.protocols :as pr]
   ;[grafter.rdf :refer [prefixer s]] 
    [grafter.rdf.io :as io ]
    [transformaciones.TransformacionGeneral :refer :all]
    [transformaciones.Predicados :refer :all]      
    [grafter.rdf :refer [prefixer]]
    ))

(use 'clojure.java.io)

(def dirMedia "direccionMedia1100")
(def humedad620 "humedad620")
(def irradia200 "irradia200")
(def presion150 "presion150")
(def sigDir1100 "sigDir1100")
(def sigVel1100 "sigVel1100")
(def temAire610 "temAire610")
(def velMax1100 "velMax1100")
(def velMedia1100 "velMedia1100")

(def uriDirMediaGen (predicate-measurement-base dirMedia)) 
(def uriHumedad620Gen (predicate-measurement-base humedad620)) 
(def uriIrradia200Gen (predicate-measurement-base irradia200)) 
(def uriPresion150Gen (predicate-measurement-base presion150)) 
(def uriSigDir1100Gen (predicate-measurement-base sigDir1100)) 
(def uriSigVel1100Gen (predicate-measurement-base sigVel1100)) 
(def uriTemAire610Gen (predicate-measurement-base temAire610)) 
(def uriVelMax1100Gen (predicate-measurement-base velMax1100)) 
(def uriVelMedia1100Gen (predicate-measurement-base velMedia1100)) 

(def dirMedia-coment (languageSpanish (str "Direccion o procedencia media del viento expresada en grados")))
(def sigDir-coment (languageSpanish (str "Sigma de la dirección del viento expresada en grados")))
(def humedad-coment (languageSpanish (str "Humedad relativa del aire expresada en porcentaje")))
(def irradia-coment (languageSpanish (str "Irradiancia global sobre una superficie plana expresada en watios por metro2")))
(def presion-coment (languageSpanish (str "Presión atmosférica expresada en milibares")))
(def sigVel-coment (languageSpanish (str "Sigma de la velocidad del viento expresada en kilómetros por hora")))
(def temAire-coment (languageSpanish (str "Temperatura del aire expresada en grados centígrados")))
(def velMax-coment (languageSpanish (str "Racha máxima del viento horizontal expresada en kilómetros por hora")))
(def velMedia-coment (languageSpanish (str "Velocidad media del viento expresada en kilómetros por hora")))

;Uri estacion
(defn station-uri[date]
  (station-base
    (str "c040-" date)))

;Uris recursos
(defn base-dirMedia[date hour] 
  (element-base
    (str date "-" hour "-" dirMedia)))

(defn base-velMax1100[date hour] 
  (element-base
    (str date "-" hour "-" velMax1100 )))

(defn base-humedad620[date hour]
  (element-base
    (str date "-" hour "-" humedad620)))

(defn base-irradia200[date hour]
  (element-base
    (str date "-" hour "-" irradia200)))

(defn base-presion150[date hour]
  (element-base
    (str date "-" hour "-" presion150)))

(defn base-sigDir1100[date hour] 
  (element-base
    (str date "-" hour "-"  sigDir1100)))

(defn base-sigVel1100[date hour] 
  (element-base
    (str date "-" hour "-" sigVel1100)))

(defn base-temAire610[date hour] 
  (element-base
    (str date "-" hour "-" temAire610)))

(defn base-velMedia1100[date hour] 
  (element-base
    (str date "-" hour "-" velMedia1100)))