# Happy Hotel

## Información del Proyecto

- **Asignatura:** DACD
- **Curso:** Segundo
- **Titulación:** GCID
- **Escuela:** EII
- **Universidad:** ULPGC

## Resumen de la Funcionalidad

El proyecto Happy Hotel integra diversos servicios para proporcionar información meteorológica, tarifas hoteleras y detalles sobre hoteles en una isla. La arquitectura consta de los siguientes componentes:

- **Prediction Provider:**
  - Realiza una solicitud a openWeatherMapAPI cada 6 horas.
  - Al recibir la información, la guarda en ActiveMQ local en un tópico llamado Weather.Prediction.

- **HappyHotelSensor:**
  - Realiza una solicitud a xoteloAPI cada 6 horas.
  - Al recibir la información, la guarda en ActiveMQ local en un tópico llamado Hotel.Rates.

- **DataLake-Builder:**
  - Se suscribe a los tópicos Weather.Prediction y Hotel.Rates.
  - Crea un DataLake con la información obtenida.

- **HappyHotelBusinessUnit:**
  - Se suscribe a los tópicos Weather.Prediction y Hotel.Rates.
  - Interactúa con el usuario ofreciendo información sobre la isla y los hoteles seleccionados.

## Argumentos o Variables de Entorno

Los argumentos necesarios para la ejecución están documentados en la descripción del release del proyecto.

