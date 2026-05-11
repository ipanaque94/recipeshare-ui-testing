# language: es
@EP-C @REC-02
Característica: REC-02 — Ver Detalle de Receta
  Como visitante quiero ver el detalle
  de una receta para seguirla

  @TC45 @smoke
  Escenario: TC45 — Todos los elementos del detalle visibles
    Dado que existe una receta publicada con datos completos
    Entonces veo portada autor título ingredientes y pasos

  @TC46
  Escenario: TC46 — Visitante no ve botones de acción
    Dado que soy visitante sin sesión en el detalle
    Entonces los botones Calificar y Comentar no están disponibles

  @TC47
  Escenario: TC47 — Usuario logueado ve botón Guardar
    Dado que soy usuario logueado viendo receta ajena
    Entonces el botón Guardar está visible y habilitado

  @TC49
  Escenario: TC49 — No dueño no ve botón Editar
    Dado que soy usuario logueado viendo receta ajena
    Entonces no veo el botón de editar