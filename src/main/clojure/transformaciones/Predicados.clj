(ns transformaciones.Predicados
      (:require [grafter.rdf :refer [prefixer]]))

;Prefijos
(def date-predicate "http://purl.org/dc/terms/date")
(def location-predicate  "http://www.w3.org/2003/01/geo/wgs84_pos#location")
(def unit-measure-predicate  "http://purl.org/linked-data/sdmx/2009/attribute#unitMeasure")
(def observation-value-predicate  "http://purl.org/linked-data/sdmx/2009/measure#obsValue")
(def milligram-cmeter-predicate  "http://dd.eionet.europa.eu/vocabulary/uom/concentration/mg.m-3")
(def micragram-cmeter-predicate  "http://dd.eionet.europa.eu/vocabulary/uom/concentration/ug.m-3")
(def percentage-predicate  "http://dbpedia.org/ontology/percentage")
(def watios-m2-predicate  "http://purl.org/iot/vocab/m3-lite#WattPerMeterSquare")
(def milibar-predicate  "http://purl.org/iot/vocab/m3-lite#Millibar")
(def km-hour-predicate  "http://purl.org/iot/vocab/m3-lite#KilometerPerHour")
(def degrees-celsius-predicate  "http://ontology.fiesta-iot.eu/ontologyDocs/m3-lite.owl#DegreeCelsius")

(def employment-contract-predicate "http://purl.org/cerif/frapo/EmploymentContract")
(def formalized-date-predicate "http://contsem.unizar.es/def/sector-publico/pproc#formalizedDate")

(def contract-economic-conditions-predicate "http://contsem.unizar.es/def/sector-publico/pproc#ContractEconomicConditions")
(def managing-dpt-predicate "http://contsem.unizar.es/def/sector-publico/pproc#managingDepartment")
(def occupation-predicate "http://dbpedia.org/ontology/occupation")
(def modified-date-predicate "http://purl.org/dc/terms/modified")
(def formalized-date-predicate "http://contsem.unizar.es/def/sector-publico/pproc#formalizedDate")
(def ending-date-predicate "http://contsem.unizar.es/def/sector-publico/pproc#actualEndDate")
(def employee-predicate "http://schema.org/employee")
(def person-resource "http://schema.org/Person")

(def vitoria-station "http://opendata.euskadi.eus/recurso/medio-ambiente/estacion/C040")

;Prefijos comunes
(def graph-base (prefixer "http://opendata.euskadi.eus/catalogo/id/"))

(def resource-measurement-base  "http://opendata.euskadi.eus/def/medio-ambiente/medicion")
(def predicate-measurement-base (prefixer "http://opendata.euskadi.eus/def/medio-ambiente/medicion/"))
(def element-base (prefixer "http://opendata.euskadi.eus/recurso/medio-ambiente/calidad-del-aire/elemento/"))

(def contract-predicate-base (prefixer "http://opendata.euskadi.eus/def/sector-publico/contrato/"))
(def contract-resource-base (prefixer "http://opendata.euskadi.eus/recurso/sector-publico/contrato/"))

(def station-base (prefixer  "http://opendata.euskadi.eus/recurso/medio-ambiente/calidad-del-aire/observation/"))

