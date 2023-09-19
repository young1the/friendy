-- '총괄관리자' 추가 (데이터가 없는 경우에만)
INSERT INTO admin_role (category)
SELECT '총괄관리자'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM admin_role WHERE category = '총괄관리자');

-- '수집담당자' 추가 (데이터가 없는 경우에만)
INSERT INTO admin_role (category)
SELECT '수집담당자'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM admin_role WHERE category = '수집담당자');

-- '문제운영자' 추가 (데이터가 없는 경우에만)
INSERT INTO admin_role (category)
SELECT '문제운영자'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM admin_role WHERE category = '문제운영자');

-- '문제검수자' 추가 (데이터가 없는 경우에만)
INSERT INTO admin_role (category)
SELECT '문제검수자'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM admin_role WHERE category = '문제검수자');
