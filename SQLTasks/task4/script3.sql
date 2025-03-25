/**
    Задача 3
    Найти всех сотрудников, которые занимают роль менеджера и имеют подчиненных (то есть число подчиненных больше 0).
    Для каждого такого сотрудника вывести следующую информацию:
    -- EmployeeID: идентификатор сотрудника.
    -- Имя сотрудника.
    -- Идентификатор менеджера.
    -- Название отдела, к которому он принадлежит.
    -- Название роли, которую он занимает.
    -- Название проектов, к которым он относится (если есть, конкатенированные в одном столбце).
    -- Название задач, назначенных этому сотруднику (если есть, конкатенированные в одном столбце).
    -- Общее количество подчиненных у каждого сотрудника (включая их подчиненных).
    -- Если у сотрудника нет назначенных проектов или задач, отобразить NULL.
**/
-- Получаем список всех менеджеров --
WITH managers AS (
    SELECT
        e.EmployeeID,
        e.Name,
        e.ManagerID,
        e.DepartmentID,
        e.RoleID
    FROM employees e
    JOIN roles r ON e.RoleID = r.RoleID
    WHERE r.RoleName = 'Менеджер'
),

-- Менеджеры с как минимум одним подчинённым
managers_with_subs AS (
    SELECT
        m.*,
        (SELECT COUNT(*)
         FROM employees s
         WHERE s.ManagerID = m.EmployeeID) AS subs_count
    FROM managers m
    WHERE EXISTS (
        SELECT 1 FROM employees
        WHERE ManagerID = m.EmployeeID
    )
)

SELECT
    mws.EmployeeID,
    mws.Name AS EmployeeName,
    mws.ManagerID,
    d.DepartmentName,
    r.RoleName,
    (
        SELECT STRING_AGG(DISTINCT p.ProjectName, ', ')
        FROM projects p
        JOIN tasks t ON p.ProjectID = t.ProjectID
        WHERE t.AssignedTo = mws.EmployeeID
    ) AS ProjectNames,
    (
        SELECT STRING_AGG(t.TaskName, ', ')
        FROM tasks t
        WHERE t.AssignedTo = mws.EmployeeID
    ) AS TaskNames,
    mws.subs_count AS TotalSubordinates
FROM managers_with_subs mws
LEFT JOIN departments d ON mws.DepartmentID = d.DepartmentID
LEFT JOIN roles r ON mws.RoleID = r.RoleID
ORDER BY mws.Name;