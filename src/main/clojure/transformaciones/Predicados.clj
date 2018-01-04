(ns transformaciones.Predicados
      (:require [grafter.rdf :refer [prefixer]]))

;Prefijos
(def purl (prefixer "http://purl.org/iot/vocab/m3-lite#"))
(def contsem (prefixer "http://contsem.unizar.es/def/sector-publico/pproc#"))
(def dbpedia (prefixer "http://dbpedia.org/ontology/"))
(def schema (prefixer "http://schema.org/"))
(def eionet (prefixer "http://dd.eionet.europa.eu/vocabulary/uom/concentration/"))
(def geo (prefixer "http://www.w3.org/2003/01/geo/wgs84_pos#"))


(def location-predicate (geo "location"))
(def milligram-cmeter-predicate  (eionet "mg.m-3"))
(def micragram-cmeter-predicate (eionet "ug.m-3"))
(def percentage-predicate  (dbpedia "percentage"))
(def occupation-predicate (dbpedia "occupation"))
(def watios-m2-predicate  (purl "PerMeterSquare"))
(def milibar-predicate  (purl "Millibar"))
(def km-hour-predicate  (purl "KilometerPerHour"))
(def formalized-date-predicate (contsem "formalizedDate"))
(def ending-date-predicate (contsem "actualEndDate"))
(def contract-economic-conditions-predicate (contsem "ContractEconomicConditions"))
(def managing-dpt-predicate (contsem "managingDepartment"))
(def employee-predicate (schema "employee"))
(def date-predicate "http://purl.org/dc/terms/date")
(def modified-date-predicate "http://purl.org/dc/terms/modified")
(def unit-measure-predicate  "http://purl.org/linked-data/sdmx/2009/attribute#unitMeasure")
(def observation-value-predicate  "http://purl.org/linked-data/sdmx/2009/measure#obsValue")
(def degrees-celsius-predicate  "http://ontology.fiesta-iot.eu/ontologyDocs/m3-lite.owl#DegreeCelsius")
(def employment-contract-predicate "http://purl.org/cerif/frapo/EmploymentContract")
(def person-predicate (schema "Person"))


(def vitoria-station "http://opendata.euskadi.eus/recurso/medio-ambiente/estacion/C040")

;Prefijos comunes
(def graph-base (prefixer "http://opendata.euskadi.eus/catalogo/id/"))

(def resource-measurement-base  "http://opendata.euskadi.eus/def/medio-ambiente/medicion")
(def predicate-measurement-base (prefixer "http://opendata.euskadi.eus/def/medio-ambiente/medicion/"))
(def element-base (prefixer "http://opendata.euskadi.eus/recurso/medio-ambiente/calidad-del-aire/elemento/"))
(def predicate-medition-associated "http://opendata.euskadi.eus/def/medio-ambiente/observacion-asociada")


(def contract-predicate-base (prefixer "http://opendata.euskadi.eus/def/sector-publico/contrato/"))
(def contract-resource-base (prefixer "http://opendata.euskadi.eus/recurso/sector-publico/contrato/"))

(def station-base (prefixer  "http://opendata.euskadi.eus/recurso/medio-ambiente/calidad-del-aire/observation/"))