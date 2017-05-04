(ns transformaciones.Prefix
      (:require [grafter.rdf :refer [prefixer]]))


;Prefijos
(def prefix-fecha  "http://purl.org/dc/terms/date")
(def prefix-localizacion  "http://www.w3.org/2003/01/geo/wgs84_pos#location")
(def prefix-unidad-medida  "http://purl.org/linked-data/sdmx/2009/attribute#unitMeasure")
(def prefix-valor-observacion  "http://purl.org/linked-data/sdmx/2009/measure#obsValue")
(def prefix-miligramo-mcubico  "http://dd.eionet.europa.eu/vocabulary/uom/concentration/mg.m-3")
(def prefix-micragramo-mcubico  "http://dd.eionet.europa.eu/vocabulary/uom/concentration/ug.m-3")
(def prefix-medicion  "http://opendata.euskadi.eus/def/medio-ambiente/medicion")
(def estacionVitoria "http://opendata.euskadi.eus/recurso/medio-ambiente/estacion/C040")
