(ns transformaciones.Predicados
      (:require [grafter.rdf :refer [prefixer]]))

;Prefijos
(def prefix-fecha  "http://purl.org/dc/terms/date")
(def prefix-localizacion  "http://www.w3.org/2003/01/geo/wgs84_pos#location")
(def prefix-unidad-medida  "http://purl.org/linked-data/sdmx/2009/attribute#unitMeasure")
(def prefix-valor-observacion  "http://purl.org/linked-data/sdmx/2009/measure#obsValue")
(def prefix-miligramo-mcubico  "http://dd.eionet.europa.eu/vocabulary/uom/concentration/mg.m-3")
(def prefix-micragramo-mcubico  "http://dd.eionet.europa.eu/vocabulary/uom/concentration/ug.m-3")

(def prefix-contrato-trabajo "http://purl.org/cerif/frapo/EmploymentContract")
(def prefix-fecha-formalizacion "http://contsem.unizar.es/def/sector-publico/pproc#formalizedDate")
(def prefix-fecha-finalizacion "http://contsem.unizar.es/def/sector-publico/pproc#formalizedDate")

(def prefix-condiciones-economicas "http://contsem.unizar.es/def/sector-publico/pproc#ContractEconomicConditions")
(def prefix-departamento-gerente "http://contsem.unizar.es/def/sector-publico/pproc#managingDepartment")
(def prefix-puesto "http://dbpedia.org/ontology/occupation")
(def prefix-fecha-modificacion "http://purl.org/dc/terms/modified")
(def prefix-empleado "http://schema.org/employee")

(def estacionVitoria "http://opendata.euskadi.eus/recurso/medio-ambiente/estacion/C040")

;Prefijos comunes
(def base-graph (prefixer "http://opendata.euskadi.eus/catalogo/id/"))

(def base-medicion-recurso  "http://opendata.euskadi.eus/def/medio-ambiente/medicion")
(def base-medicion-pred (prefixer "http://opendata.euskadi.eus/def/medio-ambiente/medicion/"))
(def base-elemento (prefixer "http://opendata.euskadi.eus/recurso/medio-ambiente/calidad-del-aire/elemento/"))

(def base-contrato-pred (prefixer "http://opendata.euskadi.eus/def/sector-publico/contrato/"))
(def base-contrato-recurso (prefixer "http://opendata.euskadi.eus/recurso/sector-publico/contrato/"))
