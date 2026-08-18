-- ============================================
-- USERS RECORD
-- ============================================

-- INSERT INTO users
--     (id, full_name, email, password, phone, role)
-- VALUES
--     (1, 'System Administrator', 'admin@telecom.com',
--      '$2a$10$dummyAdminPasswordHash',
--      '9000000001', 'ADMIN');

-- INSERT INTO users
--     (id, full_name, email, password, phone, role)
-- VALUES
--     (2, 'Demo Customer', 'customer@telecom.com',
--      '$2a$10$dummyCustomerPasswordHash',
--      '9000000002', 'CUSTOMER');


-- -- ============================================
-- -- SIM CARD
-- -- ============================================

-- INSERT INTO sim_cards
--     (id, phone_number, imsi_number, status, user_id)
-- VALUES
--     (1, '9000000002', '404450123456789', 'ACTIVE', 2);


-- ============================================
-- PLANS
-- ============================================

INSERT INTO plans
    (id, name, price, validity_days, data_limit_gb_per_day,
     talktime_mins, sms_count, plan_type, active)
VALUES
    (1, 'Prepaid Basic',
     199.00, 28, 1.5, 100, 100, 'PREPAID', true);

INSERT INTO plans
    (id, name, price, validity_days, data_limit_gb_per_day,
     talktime_mins, sms_count, plan_type, active)
VALUES
    (2, 'Prepaid Plus',
     299.00, 28, 2.0, 200, 100, 'PREPAID', true);

INSERT INTO plans
    (id, name, price, validity_days, data_limit_gb_per_day,
     talktime_mins, sms_count, plan_type, active)
VALUES
    (3, 'Postpaid Standard',
     499.00, 30, 2.5, 500, 100, 'POSTPAID', true);

INSERT INTO plans
    (id, name, price, validity_days, data_limit_gb_per_day,
     talktime_mins, sms_count, plan_type, active)
VALUES
    (4, 'Postpaid Premium',
     799.00, 30, 3.0, 1000, 200, 'POSTPAID', true);


-- ============================================
-- ACTIVE SUBSCRIPTION
-- ============================================

-- INSERT INTO subscriptions
--     (id, sim_card_id, plan_id, start_date, expiry_date,
--      remaining_data_mb, remaining_talktime_mins, status)
-- VALUES
--     (1, 1, 2,
--      CURRENT_DATE,
--      DATEADD('DAY', 28, CURRENT_DATE),
--      2048.0,
--      200,
--      'ACTIVE');


-- ============================================
-- TRANSACTION
-- ============================================

-- INSERT INTO transactions
--     (id, user_id, plan_id, amount, payment_status,
--      timestamp, payment_method)
-- VALUES
--     (1, 2, 2, 299.00, 'SUCCESS',
--      CURRENT_TIMESTAMP, 'UPI');