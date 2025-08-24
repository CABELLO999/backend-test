# Integración de React con Spring Boot

Sigue estos pasos para servir tu front-end React desde el backend de Spring Boot:

## 1. Construye el front para producción

Desde la carpeta `frontend` ejecuta:

```
npm run build
```

Esto generará una carpeta `build` con los archivos estáticos listos para producción.

## 2. Copia el contenido de `build` a Spring Boot

Copia todo el contenido de `frontend/build` a `src/main/resources/static` de tu proyecto Spring Boot:

- En Windows, puedes copiar manualmente usando el explorador de archivos.
- O con PowerShell:
  ```
  Copy-Item -Path frontend\build\* -Destination src\main\resources\static\ -Recurse
  ```

## 3. Asegúrate de que tus endpoints API empiecen por `/api/`

Esto evita conflictos entre rutas de la API y archivos estáticos del front.

## 4. Arranca tu aplicación Spring Boot

Al acceder a `http://localhost:8080/` verás tu front React servido por Spring Boot, y las llamadas a `/api/...` funcionarán igual.

---

**Nota:** Si usas React Router, deberás configurar un controlador en Spring Boot para redirigir rutas no API a `index.html`.

¿Necesitas ayuda con la configuración para React Router o automatizar la copia de archivos?
