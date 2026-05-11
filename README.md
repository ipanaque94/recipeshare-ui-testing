# RecipeShare — UI Testing con Selenium + Cucumber

> Automatización E2E de la interfaz de usuario de
> RecipeShare aplicando BDD con Gherkin y Page Object Model.
> Complementa las pruebas de API validando lo que el
> usuario ve y experimenta en pantalla.

**Stack:** Java 17 · Selenium 4 · Cucumber 7 · TestNG · Allure  
**Sitio:** https://tusrecetas.lovable.app  
**QA:** Enoc Isaac Ipanaque Rodas

---

## Lo que aprendí haciendo este proyecto

La decisión más importante no fue escribir código —
fue decidir **qué no automatizar en UI**.

Aprendí que duplicar en Selenium lo que ya probé
en Rest Assured no agrega valor: solo agrega tiempo
de ejecución y mantenimiento. En UI solo van los
TC que requieren interacción visual o verificación
en pantalla.

También aprendí que los **bugs se prueban en ambas
capas** porque un bug de API puede manifestarse
diferente en la UI — y viceversa.

---

## Lo que analicé antes de escribir código

De los 44 TC del plan manual analicé cuáles
pertenecían a cada capa:
API (Rest Assured):    validaciones de datos y lógica
UI (Selenium):         flujos visuales, redirecciones,
subida de archivos, control de acceso
Ambas capas:           TC críticos de negocio

Resultado: 20 TC en UI, 0 duplicados innecesarios.


**Ninguna capa hace el trabajo de otra.**
Eso es lo que permite cambiar un selector sin tocar
los tests, o agregar un nuevo escenario sin tocar
las pages.

---

## Decisiones técnicas profesionales

**¿Por qué Page Object Model?**
Si el sitio cambia un selector, solo cambio la page.
Los 24 tests que usan ese elemento siguen funcionando
sin tocarlos.

**¿Por qué ThreadLocal en DriverManager?**
Permite ejecutar tests en paralelo sin que los
threads se interfieran entre sí. Es el estándar
en equipos profesionales.

**¿Por qué tags en los features?**
Permiten ejecutar solo smoke, solo bugs,
solo una épica, o excluir bugs conocidos del
pipeline CI/CD sin eliminar los tests.

**¿Por qué screenshot automático en Hooks?**
Porque cuando un test falla en CI/CD no puedes
ver la pantalla. El screenshot en el reporte
Allure es la única evidencia de qué pasó.

---

## Bugs confirmados también en UI

| Bug | Historia | Descripción | Severidad |
|---|---|---|---|
| BUG-001 | AUTH-01 | UI acepta password < 8 chars | Alta |
| BUG-002 | AUTH-02 | Checkbox Recordarme no existe | Alta |
| BUG-003 | AUTH-01 | Mensaje revela email existente | Alta |
| BUG-004 | PROF-02 | UI acepta nombre > 60 chars | Media |
| BUG-005 | PROF-02 | UI acepta bio > 300 chars | Media |
| BUG-006 | PROF-02 | UI acepta avatar > 5 MB | Alta |
| BUG-007 | REC-01 | UI acepta título > 100 chars | Alta |
| BUG-008 | REC-01 | UI acepta descripción > 500 chars | Alta |

---

## Cobertura UI por épica

| Épica | Historia | TC en UI |
|---|---|---|
| EP-A | AUTH-01 Registro | TC1,TC2,TC3,TC4,TC5,TC6,TC7 |
| EP-A | AUTH-02 Login | TC8,TC9,TC10,TC11,TC13 |
| EP-A | AUTH-03 Logout | TC15,TC16,TC17 |
| EP-B | PROF-02 Editar Perfil | TC21,TC22,TC23,TC24,TC25,TC26,TC27,TC28,TC29 |
| EP-C | REC-01 Crear Receta | TC33,TC34,TC35,TC36,TC37,TC38,TC39,TC43,TC44 |
| EP-C | REC-02 Ver Detalle | TC45,TC46,TC47,TC49 |
| EP-C | REC-03 Editar Receta | TC48,TC50 |
| **Total** | | **20 TC** |

---

## Ejecutar

```bash
# Todos los tests
mvn clean test

# Solo smoke — flujos críticos
mvn test -Dcucumber.filter.tags="@smoke"

# Solo bugs conocidos
mvn test -Dcucumber.filter.tags="@BUG-001 or @BUG-002"

# Excluir bugs del pipeline
mvn test -Dcucumber.filter.tags="not @BUG-001 \
  and not @BUG-002 and not @BUG-003"

# Reporte Allure
mvn allure:serve
```
<img width="1350" height="681" alt="Captura de pantalla 2026-05-11 145145" src="https://github.com/user-attachments/assets/0355f041-c8a4-4b54-b62d-328d2e0b2415" />
<img width="1353" height="684" alt="Captura de pantalla 2026-05-11 145129" src="https://github.com/user-attachments/assets/3a83dd13-3408-4935-81d0-bdc31c8bd5f7" />

---

## Proyecto relacionado

Test probadas en la capa API:

[Ver API Testing →](https://github.com/ipanaque94/recipeshare-api-testing)

---

[LinkedIn](https://linkedin.com/in/enoc-isaac-ipanaque-rodas-b3729a283)
| [GitHub](https://github.com/ipanaque94)
