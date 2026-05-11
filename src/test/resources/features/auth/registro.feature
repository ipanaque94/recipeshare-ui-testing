# language: es
@EP-A @auth @registro
Característica: AUTH-01 — Registro de cuenta

  @TC1 @regresion
  Escenario: TC1 — Registro exitoso con datos válidos
    Dado que el usuario está en la página de registro
    Cuando ingresa un email nuevo válido y password "Password1!"
    Y hace clic en Registrarse
    Entonces debería permanecer en auth sin errores

  @TC3 @bug @TRW-26
  Escenario: TC3 — BVA password 7 chars — bug conocido
    Dado que el usuario está en la página de registro
    Cuando ingresa un email nuevo válido y password "Pass12!"
    Y hace clic en Registrarse
    Entonces debería ver un mensaje de error de validación de contraseña

  @TC4 @bva
  Escenario: TC4 — BVA password 8 chars exactos — válido
    Dado que el usuario está en la página de registro
    Cuando ingresa un email nuevo válido y password "Pass123!"
    Y hace clic en Registrarse
    Entonces debería permanecer en auth sin errores

  @TC6
  Escenario: TC6 — Email duplicado
    Dado que el usuario está en la página de registro
    Cuando ingresa el email ya registrado y password "Password1!"
    Y hace clic en Registrarse
    Entonces debería ver un mensaje de error de email duplicado