# language: es
@EP-A @AUTH-03
Característica: AUTH-03 — Logout
  Como usuario quiero cerrar sesión
  para proteger mi cuenta

  @TC15 @smoke
  Escenario: TC15 — Logout destruye sesión
    Dado que soy usuario logueado en el sistema
    Cuando hago clic en cerrar sesión
    Entonces la sesión se destruye y redirige al inicio

  @TC16
  Escenario: TC16 — URL protegida tras logout
    Dado que soy usuario logueado en el sistema
    Cuando hago clic en cerrar sesión
    Y intento acceder directamente a "/perfil"
    Entonces el sistema redirige a login o pantalla de inicio