# language: es
@EP-C @REC-03
Característica: REC-03 — Editar Receta
  Como autor quiero editar mi receta
  para corregirla o mejorarla

  @TC48 @smoke
  Escenario: TC48 — Autor edita su propia receta
    Dado que soy el autor logueado en editar receta
    Cuando cambio el título a "Lomo Saltado Editado UI"
    Y hago clic en guardar cambios
    Entonces los cambios se reflejan inmediatamente en el detalle

  @TC50
  Escenario: TC50 — Cambios reflejados inmediatamente
    Dado que soy el autor logueado en editar receta
    Cuando cambio el título a "Titulo Actualizado QA"
    Y hago clic en guardar cambios
    Entonces los cambios se reflejan inmediatamente en el detalle