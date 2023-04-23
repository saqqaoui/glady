INSERT INTO public.companies(id, balance, created_at) VALUES
                                              (1, 1000.00, NOW()),
                                              (2, 2000.00, NOW()),
                                              (3, 3000.00, NOW());

INSERT INTO public.employees (id, company_id, created_at) VALUES
                                               (1, 1, NOW()),
                                               (2, 1, NOW()),
                                               (3, 2, NOW()),
                                               (4, null, NOW());

INSERT INTO public.payments (company_id, deposit_type, employee_id, amount, created_at)
VALUES
    (1, 'GIFT', 1, 90, '2022-12-31'),
    (1, 'GIFT', 1, 90, '2020-12-31'),
    (1, 'MEAL', 1, 200, NOW()),
    (2, 'GIFT', 3, 200, '2021-12-31');