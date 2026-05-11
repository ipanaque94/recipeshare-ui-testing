# language: es
@EP-B @perfil
Característica: PROF-02 — Editar perfil

  @TC21 @bva
  Escenario: TC21 — Nombre 60 chars exactos — válido
    Dado que el usuario autenticado está en su página de perfil
    Cuando ingresa un nombre de exactamente 60 caracteres
    Y guarda los cambios
    Entonces debería ver el mensaje de guardado exitoso

  @TC22 @bva @bug @TRW-28
  Escenario: TC22 — Nombre 61 chars — bug conocido
    Dado que el usuario autenticado está en su página de perfil
    Cuando ingresa un nombre de 61 caracteres
    Y guarda los cambios
    Entonces el frontend debería mostrar un error de validación de longitud

  @TC23 @bva
  Escenario: TC23 — Bio 300 chars — válido
    Dado que el usuario autenticado está en su página de perfil
    Cuando ingresa una bio de exactamente 300 caracteres
    Y guarda los cambios
    Entonces debería ver el mensaje de guardado exitoso

  @TC24 @bva @bug @TRW-29
  Escenario: TC24 — Bio 301 chars — bug conocido
    Dado que el usuario autenticado está en su página de perfil
    Cuando ingresa una bio de 301 caracteres
    Y guarda los cambios
    Entonces el frontend debería mostrar un error de validación de longitud