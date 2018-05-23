# Generación, Evaluación y Explotación de Open Linked Data a partir de datos publicados por Open Data Euskadi

Trabajo de fin de grado correspondiente a la finalización de los estudios de Ing. Informática de Gestión y Sistemas de Información. La documentación total del proyecto se puede encontrar en: https://addi.ehu.es/handle/10810/25788 .

## Resumen trabajo

A diario múltiples organizaciones tanto públicas como privadas publican Datos Abiertos, incrementándose el número de ellas que eligen hacerlo a través de Datos Enlazados. Este nuevo método tiene como objetivo la interconexión, reutilización e integración de la información para crear una gran base de datos interconectada y distribuida que pueda ser consumida tanto por personas como por máquinas. 

En este contexto, este Trabajo de Fin de Grado (TFG) surge durante la realización de unas prácticas de cooperación educativa en la empresa Eurohelp Consulting, a la que un cliente, Open Data Euskadi, solicito la mejora de los medios en los que publica la información de la que dispone. Como solución a esta solicitud, se propuso la creación de representaciones de datos públicos publicados por Open Data Euskadi en RDF. 

El RDF generado se convertirá posteriormente en Datos Enlazados a través de la creación de nexos entre éstos con la información existente en la web. El diseño de dicha solución dio lugar a este TFG desarrollado durante las prácticas de cooperación educativa.
 Para ello, se partió de ciertos conjuntos de datos existentes en el portal de Open Data Euskadi referentes a la calidad del aire, registros de contratos, estaciones meteorológicas y retribuciones nominativas de altos cargos del gobierno. A partir de ellos, como resultado del TFG, se obtuvo RDF de calidad interconectado, en medida de lo posible, con datos relacionados. Se consiguió, además, presentar la información generada en distintas formas que facilitaban al usuario su compresión, consumo, explotación y manipulación.

En cuanto a los aspectos metodológicos, se utilizó Grafter para la generación de RDF, SHACL para la validación de la información creada y Silk para el descubrimiento de enlaces. Para la visualización gráfica se usó la librería D3.js para la creación de gráficos en forma de grafos dinámicos.
