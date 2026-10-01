# Escáner de Red Local en Java

Aplicación de escritorio con interfaz gráfica (Java Swing) diseñada para auditar y analizar rangos de direcciones IP en una red local (LAN).

## Características

- **Escaneo por rango IPv4:** Búsqueda entre una IP de inicio y una IP de fin.
- **Comprobación de estado:** Identifica equipos activos en la red e indica la latencia de respuesta en milisegundos.
- **Resolución de nombres:** Muestra el nombre de host (*Hostname*) detectado por DNS inverso.
- **Concurrencia:** Implementa `SwingWorker` para mantener la interfaz fluida durante la búsqueda.
- **Filtro y exportación:** Permite filtrar para ver solo los dispositivos activos y exportar el informe a un archivo `.csv`.

## Requisitos de Ejecución

- Java Development Kit (JDK) 11 o superior.
- Eclipse IDE u otro entorno compatible con Java.

## Cómo Ejecutar

1. Clona o descarga el repositorio.
2. Abre el proyecto en tu IDE (ej. Eclipse).
3. Dirígete a `src/ui/EscanerRedFrame.java`.
4. Ejecuta la clase (`Run As > Java Application`).

## Estructura del Proyecto

```text
EscanerRed/
├── src/
│   ├── models/
│   │   └── Dispositivo.java
│   ├── services/
│   │   └── NetworkScannerService.java
│   └── ui/
│       └── EscanerRedFrame.java
├── docs/
│   ├── Documentacion_Tecnica.pdf
│   └── Manual_de_Usuario.pdf
└── README.md
