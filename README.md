# TP_REDES
#  Escáner de Red Local (Java Swing)

Aplicación gráfica desarrollada en **Java** para auditar y escanear direcciones IP dentro de una red local (LAN). Permite identificar equipos activos, resolver nombres de host vía DNS inverso, medir latencias de respuesta y exportar los resultados a formato CSV.

---

##  Funcionalidades

- **Escaneo por rango IPv4:** Define una IP de inicio y una IP de fin para auditar la subred.
- **Verificación de estado (ICMP Ping):** Comprueba si los dispositivos se encuentran encendidos y alcanzables.
- **Resolución DNS inversa:** Obtiene automáticamente el nombre del equipo (*Hostname*).
- **Procesamiento concurrente:** Utiliza `SwingWorker` para realizar peticiones de red en segundo plano sin congelar la interfaz gráfica.
- **Visualización en tiempo real:** Barra de progreso y contador dinámico de equipos activos.
- **Filtro rápido:** Opción para alternar entre mostrar la lista completa o únicamente los equipos activos.
- **Exportación de datos:** Guarda el reporte de escaneo en un archivo `.csv`.

---

##  Tecnologías y Herramientas

- **Lenguaje:** Java (JDK 11 o superior)
- **Interfaz Gráfica:** Java Swing / AWT
- **Concurrencia:** `javax.swing.SwingWorker`
- **Redes:** `java.net.InetAddress`
- **IDE Utilizado:** Eclipse IDE / Visual Studio Code

---

##  Estructura del Proyecto

El código está organizado siguiendo una arquitectura por capas (MVC-like):

```text
EscanerRed/
├── src/
│   ├── models/
│   │   └── Dispositivo.java              # Modelo de datos del equipo escaneado
│   ├── services/
│   │   └── NetworkScannerService.java    # Lógica de red, regex y conversión de IP
│   └── ui/
│       └── EscanerRedFrame.java          # Interfaz gráfica (Swing) y gestión de hilos
├── docs/
│   ├── Documentacion_Tecnica.pdf         # Documentación de arquitectura y código
│   └── Manual_de_Usuario.pdf             # Guía paso a paso para el usuario final
├── .gitignore
└── README.md
