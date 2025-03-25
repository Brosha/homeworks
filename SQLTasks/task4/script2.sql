/**
    Задача 2
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
),

-- Подсчёт прямых подчиненных для каждого руководителя
subordinates_counts AS (
    SELECT
        ManagerID,
        COUNT(*) AS subordinate_count
    FROM employees
    GROUP BY ManagerID
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
    ) AS TaskNames,
    (
        SELECT COUNT(*)
        FROM tasks t
        WHERE t.AssignedTo = h.EmployeeID
    ) AS TotalTasks,
    COALESCE(sc.subordinate_count, 0) AS TotalSubordinates
FROM hierarchy h
LEFT JOIN departments d ON h.DepartmentID = d.DepartmentID
LEFT JOIN roles r ON h.RoleID = r.RoleID
LEFT JOIN subordinates_counts sc ON h.EmployeeID = sc.ManagerID
ORDER BY h.Name;