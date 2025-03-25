/**
    Задача 1
    Найти всех сотрудников, подчиняющихся Ивану Иванову (с EmployeeID = 1), включая их подчиненных и подчиненных подчиненных.
    Для каждого сотрудника вывести следующую информацию:
    -- EmployeeID: идентификатор сотрудника.
    -- Имя сотрудника.
    -- ManagerID: Идентификатор менеджера.
    -- Название отдела, к которому он принадлежит.
    -- Название роли, которую он занимает.
    -- Название проектов, к которым он относится (если есть, конкатенированные в одном столбце через запятую).
    -- Название задач, назначенных этому сотруднику (если есть, конкатенированные в одном столбце через запятую).
    -- Если у сотрудника нет назначенных проектов или задач, отобразить NULL.
**/
-- Получаем иерархию подчинения --
WITH RECURSIVE hierarchy AS (
    SELECT
        e.EmployeeID,
        e.Name,
        e.ManagerID,
        e.DepartmentID,
        e.RoleID
    FROM employees e
    WHERE e.ManagerID = 1

    UNION ALL

    SELECT
        e.EmployeeID,
        e.Name,
        e.ManagerID,
        e.DepartmentID,
        e.RoleID
    FROM employees e
    JOIN hierarchy h ON e.ManagerID = h.EmployeeID
)

SELECT
    h.EmployeeID,
    h.Name AS EmployeeName,
    h.ManagerID,
    d.DepartmentName,
    r.RoleName,
    (
        SELECT STRING_AGG(DISTINCT p.ProjectName, ', ')
        FROM projects p
        JOIN tasks t ON p.ProjectID = t.ProjectID
        WHERE t.AssignedTo = h.EmployeeID
    ) AS ProjectNames,
    (
        SELECT STRING_AGG(t.TaskName, ', ')
        FROM tasks t
        WHERE t.AssignedTo = h.EmployeeID
    ) AS TaskNames
FROM hierarchy h
LEFT JOIN departments d ON h.DepartmentID = d.DepartmentID
LEFT JOIN Roles r ON h.RoleID = r.RoleID
ORDER BY h.Name;