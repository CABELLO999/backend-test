# Entidades del Dominio

A continuación se describen las principales entidades del dominio para la gestión del parking:

## Vehicle (Vehículo)
Representa un vehículo que puede ser oficial, residente o no residente.
- **plate**: Matrícula del vehículo (clave primaria).
- **type**: Tipo de vehículo (`OFICIAL`, `RESIDENTE`, `NO_RESIDENTE`).
- **residentAccumulatedMinutes**: Minutos acumulados de estacionamiento (solo para residentes).

## Stay (Estancia)
Representa una estancia (entrada y salida) de un vehículo en el parking.
- **id**: Identificador único de la estancia.
- **plate**: Matrícula del vehículo asociado a la estancia.
- **entryTime**: Fecha y hora de entrada (tipo `Calendar`).
- **exitTime**: Fecha y hora de salida (tipo `Calendar`).

## VehicleType (Enumeración)
Enumera los tipos posibles de vehículo:
- **OFICIAL**: Vehículo oficial.
- **RESIDENTE**: Vehículo de residente.
- **NO_RESIDENTE**: Vehículo no residente.

## Resident (Residente)
Representa a un residente del parking (si se usa modelo extendido).
- **id**: Identificador único.
- **name**: Nombre del residente.
- **payments**: Lista de pagos realizados.

## Payment (Pago)
Representa un pago realizado por un residente.
- **id**: Identificador único del pago.
- **amount**: Importe pagado.
- **date**: Fecha del pago.
- **resident**: Referencia al residente que realizó el pago.

## DTOs (Objetos de Transferencia de Datos)
Se utilizan para la comunicación entre frontend y backend:

- **EntryRequestDTO**: Petición para registrar entrada de vehículo.
	- plate: Matrícula del vehículo.
- **StayResponseDTO**: Respuesta al registrar una estancia.
	- id: Identificador de la estancia.
	- plate: Matrícula del vehículo.
	- entryTime: Fecha y hora de entrada.

## Relación entre entidades
- Un `Vehicle` puede tener varias `Stay` asociadas (relación 1 a N).
- Cada `Stay` está asociada a un único `Vehicle` mediante la matrícula.
- Un `Resident` puede tener varios `Payment` asociados.

Todas estas entidades están mapeadas con JPA para su persistencia en la base de datos y son la base para la lógica de negocio de la aplicación.
