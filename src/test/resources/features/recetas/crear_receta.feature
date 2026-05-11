# language: es

@EP-C @recetas
Característica: REC-01 — Crear receta

  @TC33 @smoke
  Escenario: TC33 — Crear receta básica

    Dado que el usuario autenticado está en el formulario de crear receta

    Cuando completa todos los campos obligatorios con datos válidos

    Y guarda la receta

    Entonces debería ver la receta creada con sus datos correctos