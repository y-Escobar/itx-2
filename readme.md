# ITX - Caso Práctico Backend Tools 2025

## Descripción del proyecto

Este proyecto implementa una **API REST** para ordenar un listado de productos de una categoría de camisetas según diferentes **criterios ponderados**, tal y como se indica en el enunciado del caso práctico de ITX.  

Los criterios actualmente implementados son:
- **Ventas por unidades (`salesUnits`)**
- **Ratio de stock (`stockRatio`)**

La puntuación final de cada producto se calcula mediante una **suma ponderada** de ambos criterios.  
El resultado se devuelve en una lista ordenada de productos, expuesta a través de un **endpoint REST `/products`**.

---

## Enfoque y arquitectura

El desarrollo se ha realizado siguiendo los principios de **Arquitectura Hexagonal (Ports & Adapters)** y los **principios tácticos de DDD**, con el objetivo de mantener un código limpio, modular y fácilmente extensible.

### Estructura principal

```
src/
├── main/
│   ├── java/com/escobar/itx_2/products/
│   │   ├── application/
│   │   │   └── ListProducts.java
│   │   ├── domain/
│   │   │   ├── primitives/
│   │   │   │   └── ProductPrimitive.java
│   │   │   ├── Product.java
│   │   │   ├── ProductId.java
│   │   │   ├── ProductName.java
│   │   │   ├── ProductRead.java
│   │   │   ├── ProductRepository.java
│   │   │   ├── ProductSalesUnits.java
│   │   │   ├── ProductSize.java
│   │   │   ├── ProductSortingCriteria.java
│   │   │   ├── ProductStock.java
│   │   │   └── ProductStockAmount.java
│   │   ├── infrastructure/
│   │   │   ├── config/
│   │   │   │   └── DataInitializer.java
│   │   │   ├── controller/
│   │   │   │   └── ProductController.java
│   │   │   │── dto/
│   │   │   │   │── ListProductsResponseDTO.java
│   │   │   │   └── ProductSortingWeights.java
│   │   │   │── entity/
│   │   │   │   └── ProductEntity.java
│   │   │   └── repository/
│   │   │       └── MongoProductRepository.java
│   │   │       └── ProductDomainRepository.java
│   │   └── Itx2Application.java
│   └── resources/
│       └── application.yml
└── test/
    ├── unit/
    ├── integration/
    └── e2e/
```

### Capas y responsabilidades

- **Domain**  
  - Contiene la lógica de negocio pura: modelo `Product` y los criterios de puntuación asociados a estos en la clase `ProductSortingCriteria`.
  - He decidido crear `ProductCriteriaSort`como Enum porque me ha parecido una forma escalable de introducir criterios, ya que éstos dependen directamente de alguno de los atributos relacionados con `Product`.
  - Para introducir un nuevo criterio tan solo tendríamos que añadir ese atributo al Record `ProductSortingWeights` e incluir el Enum relacionado al mismo añadiendo la lógica del valor que debe recuperar para luego multiplicarle el valor del peso.

- **Application**  
  El caso de uso principal `ListProducts` coordina la lógica de ordenación, combinando los resultados de los criterios según los pesos recibidos como parámetros.

- **Infrastructure**
  - `ProductDomainRepository`: adaptador MongoDB para obtener los productos almacenados.  
  - `ProductController`: capa REST que expone el endpoint principal `/products`.

---

## Endpoint principal

**GET** `/products`

### Parámetros

| Parámetro      | Tipo  | Descripción |
|----------------|-------|--------------|
| `salesUnits`   | double | Peso del criterio de ventas |
| `stockRatio`   | double | Peso del criterio de ratio de stock |

### Ejemplo de uso

```
GET /products?salesUnits=1.0&stockRatio=1.0
```

### Ejemplo de respuesta

```json
{
  "products": [
    {
      "id": "3d21ffa2-1abc-4e5a-ba95-515773330cf0",
      "name": "CONTRASTING LACE T-SHIRT",
      "salesUnits": 650,
      "stock": {
        "SMALL": 0,
        "MEDIUM": 1,
        "LARGE": 0
      }
    },
    {
      "id": "4945ee60-ea5d-4466-b9eb-0c97bcf9e959",
      "name": "V-NECH BASIC SHIRT",
      "salesUnits": 100,
      "stock": {
        "SMALL": 4,
        "MEDIUM": 9,
        "LARGE": 0
      }
    },
    ...
  ]
}
```

---

## Testing

El proyecto incluye **tres niveles de pruebas**, cubriendo desde la lógica de negocio hasta el endpoint final.

### 1. Unitarios
Verifican la correcta puntuación de los criterios (`ProductSortingCriteria`) y la lógica de `ListProducts`.

### 2. Integración
Comprueban la interacción entre los distintos componentes (repositorio, aplicación y controlador).

### 3. End-to-End (Rest Assured)
Prueban el flujo completo del endpoint `/products` con diferentes combinaciones de pesos, validando orden y tamaño del resultado.

---

## Persistencia

Se utiliza **MongoDB** como base de datos, con un repositorio de productos precargado.

---

## Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.5.7**
- **MongoDB**
- **JUnit 5 / Mockito**
- **Rest Assured** para E2E
- **Gradle** como herramienta de build
- **Docker**
- **Swagger**

---

## Ejecución

### Requisitos
- Java 21
- MongoDB activo en `localhost:27017`
- Docker

### Ejecución local

Para ejecutar localmente es necesario levantar contenedores de Docker, ya que la instancia de MongoDB la levantaremos aquí.
En mi caso he estado empleando un pequeño Script de "re-arranque":
```
$ErrorActionPreference = "Stop"
$env:SPRING_PROFILES_ACTIVE = "dev"

Write-Host "Bajando stack..."
docker compose down --remove-orphans

Write-Host "Limpiando y construyendo JAR (sin tests)..."
./gradlew.bat clean assemble -x test

Write-Host "Reconstruyendo imagen backend (sin caché)..."
docker compose build --no-cache --pull backend

Write-Host "Levantando en segundo plano..."
docker compose up -d
```

La aplicación en Local por defecto se desplegará en [http://localhost:8080](http://localhost:8080)
<br>
La API estará disponible en: [http://localhost:8080/api/products](http://localhost:8080/api/products)

### Ejecución de tests

```
./gradlew test
```

---

## Notas adicionales

Seguí el feedback que me proporcionaron en la prueba anterior corrigiendo punto por punto las indicaciones

1. Reduje los niveles de indirección al incluir solo aquellas clases necesarias con tal de cumplir criterios de Fewest Elements
2. Eliminé clases sin uso evitando código innecesario.
3. Modifiqué el Naming de las clases para que fuesen más claras y concisas.
4. El Endpoint de /products emplea @GetMapping siguiendo estándares REST.
5. Mejoré el diseño del análisis de criterios para adecuarse más a los términos de OCP.
6. Reduje los tests a los necesarios evitando Mockeos innecesarios y análisis sobre elementos anémicos.


## Repositorio: [github.com/y-Escobar/itx-2](https://github.com/y-Escobar/itx-2)

