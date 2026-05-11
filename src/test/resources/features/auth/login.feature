# language: es
@EP-A @auth @login
Característica: AUTH-02 — Login

  @TC8 @regresion @smoke
  Escenario: TC8 — Login exitoso
    Dado que el usuario está en la página de login
    Cuando ingresa credenciales válidas
    Y hace clic en Iniciar sesión
    Entonces debería ver la página de inicio con sesión iniciada

  @TC10
  Escenario: TC10 — Password incorrecto
    Dado que el usuario está en la página de login
    Cuando ingresa email válido y password incorrecto "Incorrecto1"
    Y hace clic en Iniciar sesión
    Entonces debería ver el mensaje "Credenciales incorrectas"
    Y no debería estar autenticado

  @TC13
  Escenario: TC13 — Campos vacíos en login
    Dado que el usuario está en la página de login
    Cuando hace clic en Iniciar sesión sin completar campos
    Entonces debería ver mensajes de validación en los campos requeridos