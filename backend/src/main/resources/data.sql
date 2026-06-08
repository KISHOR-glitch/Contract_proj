-- Seed Data for Contracts Management System Module

-- Clear existing data
TRUNCATE TABLE workflow_history CASCADE;
TRUNCATE TABLE contracts CASCADE;

-- Insert Contracts
INSERT INTO contracts (id, title, description, status, owner_name, created_at, updated_at) VALUES
('9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', 'Vendor Service Agreement - Acme Corp', 'Annual services agreement for office IT support, server maintenance, and emergency troubleshooting.', 'APPROVED', 'Alice Smith', '2026-05-01 09:00:00', '2026-05-05 14:30:00'),
('a3fde062-811c-43f1-b9ff-a0350d75a1a1', 'Software Licensing Agreement - JetBrains', 'Multi-user licenses for IntelliJ IDEA, WebStorm, and CLion IDEs for the engineering team.', 'REVIEW', 'Alice Smith', '2026-05-10 10:15:00', '2026-05-12 11:00:00'),
('b78e826b-80df-4d6d-8c43-c0d1b11e2202', 'Marketing Consultant Contract - CreativeMinds', 'Retainer agreement for digital marketing campaign optimization, SEO, and weekly performance reporting.', 'DRAFT', 'Charlie Brown', '2026-06-01 14:00:00', '2026-06-01 14:00:00'),
('c90f845d-1c39-4d6d-b2a1-0ea4fbcd3bcf', 'Office Lease Agreement - 5th Avenue HQ', 'Commercial lease for the main corporate office on 5th Avenue, valid for 5 years with renewal options.', 'APPROVED', 'Sarah Jenkins', '2026-04-10 08:30:00', '2026-04-20 16:45:00'),
('d0124a91-49b8-4c8d-8fe0-2bde6fbc01e2', 'Mutual Non-Disclosure Agreement - Stark Industries', 'Standard mutual NDA to cover exploratory partnership discussions regarding clean energy tech.', 'REJECTED', 'Tony Stark', '2026-05-15 11:30:00', '2026-05-18 10:00:00'),
('e1235b02-5ac9-4e9e-90f1-3cdf7fcd02f3', 'Employment Agreement - Jane Doe (CTO)', 'Executive employment contract detailing role, compensation, stock options, and benefits package.', 'APPROVED', 'Sarah Jenkins', '2026-05-20 09:00:00', '2026-05-25 15:00:00'),
('f2346c13-6bd0-4fad-91a2-4def8fde03f4', 'Catering Services Agreement - Foodies Delight', 'Single-event catering contract for the upcoming annual corporate gala dinner.', 'TERMINATED', 'David Miller', '2026-03-01 10:00:00', '2026-03-25 17:00:00'),
('01237d24-7ce1-4cbf-a2b3-5ef90fef04f5', 'Cloud Infrastructure Service Contract - AWS', 'Enterprise agreement for hosting, EC2 instances, S3 storage, and RDS databases.', 'REVIEW', 'Bob Johnson', '2026-06-02 11:00:00', '2026-06-04 15:30:00'),
('12348e35-8df2-4d00-b3c4-6f0a1f0f05f6', 'Graphic Design Retainer - PixelPerfect', 'Monthly retainer for ad-hoc graphic design assets, social media banners, and UI mockups.', 'DRAFT', 'Charlie Brown', '2026-06-05 16:00:00', '2026-06-05 16:00:00'),
('23459f46-9e03-4e11-b4d5-7f1b2f1f06f7', 'Affiliate Marketing Agreement - PartnerWeb', 'Revenue-share agreement for the referral traffic program targeting developer tools.', 'REVIEW', 'David Miller', '2026-06-04 09:30:00', '2026-06-06 14:00:00'),
('34560a57-0f14-4f22-c5e6-8f2c3f2f07f8', 'External Security Audit Contract - SecOps', 'Engagement for the Q3 penetration testing and vulnerability assessment of the API platform.', 'APPROVED', 'Bob Johnson', '2026-05-02 10:00:00', '2026-05-10 11:20:00'),
('45671b68-1f25-4f33-d6f7-9f3d4f3f08f9', 'IP Licensing Agreement - PatentHoldings LLC', 'Exclusive licensing rights for proprietary compression algorithms used in file-transfer modules.', 'REJECTED', 'Alice Smith', '2026-04-15 13:00:00', '2026-04-22 09:15:00');

-- Insert Workflow History records
INSERT INTO workflow_history (id, contract_id, previous_status, new_status, changed_by, changed_at) VALUES
-- Vendor Service Agreement
('a1111111-1111-1111-1111-111111111111', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', NULL, 'DRAFT', 'Alice Smith', '2026-05-01 09:00:00'),
('a1111111-1111-1111-1111-111111111112', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', 'DRAFT', 'REVIEW', 'Alice Smith', '2026-05-02 10:30:00'),
('a1111111-1111-1111-1111-111111111113', '9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d', 'REVIEW', 'APPROVED', 'Bob Johnson (VP Engineering)', '2026-05-05 14:30:00'),

-- Software Licensing Agreement
('b2222222-2222-2222-2222-222222222221', 'a3fde062-811c-43f1-b9ff-a0350d75a1a1', NULL, 'DRAFT', 'Alice Smith', '2026-05-10 10:15:00'),
('b2222222-2222-2222-2222-222222222222', 'a3fde062-811c-43f1-b9ff-a0350d75a1a1', 'DRAFT', 'REVIEW', 'Alice Smith', '2026-05-12 11:00:00'),

-- Marketing Consultant Contract
('c3333333-3333-3333-3333-333333333331', 'b78e826b-80df-4d6d-8c43-c0d1b11e2202', NULL, 'DRAFT', 'Charlie Brown', '2026-06-01 14:00:00'),

-- Office Lease Agreement
('d4444444-4444-4444-4444-444444444441', 'c90f845d-1c39-4d6d-b2a1-0ea4fbcd3bcf', NULL, 'DRAFT', 'Sarah Jenkins', '2026-04-10 08:30:00'),
('d4444444-4444-4444-4444-444444444442', 'c90f845d-1c39-4d6d-b2a1-0ea4fbcd3bcf', 'DRAFT', 'REVIEW', 'Sarah Jenkins', '2026-04-12 09:15:00'),
('d4444444-4444-4444-4444-444444444443', 'c90f845d-1c39-4d6d-b2a1-0ea4fbcd3bcf', 'REVIEW', 'APPROVED', 'Robert Davis (Finance Board)', '2026-04-20 16:45:00'),

-- Mutual NDA
('e5555555-5555-5555-5555-555555555551', 'd0124a91-49b8-4c8d-8fe0-2bde6fbc01e2', NULL, 'DRAFT', 'Tony Stark', '2026-05-15 11:30:00'),
('e5555555-5555-5555-5555-555555555552', 'd0124a91-49b8-4c8d-8fe0-2bde6fbc01e2', 'DRAFT', 'REVIEW', 'Tony Stark', '2026-05-16 12:00:00'),
('e5555555-5555-5555-5555-555555555553', 'd0124a91-49b8-4c8d-8fe0-2bde6fbc01e2', 'REVIEW', 'REJECTED', 'Legal Department', '2026-05-18 10:00:00'),

-- Employment Agreement
('f6666666-6666-6666-6666-666666666661', 'e1235b02-5ac9-4e9e-90f1-3cdf7fcd02f3', NULL, 'DRAFT', 'Sarah Jenkins', '2026-05-20 09:00:00'),
('f6666666-6666-6666-6666-666666666662', 'e1235b02-5ac9-4e9e-90f1-3cdf7fcd02f3', 'DRAFT', 'REVIEW', 'Sarah Jenkins', '2026-05-21 10:30:00'),
('f6666666-6666-6666-6666-666666666663', 'e1235b02-5ac9-4e9e-90f1-3cdf7fcd02f3', 'REVIEW', 'APPROVED', 'HR Director', '2026-05-25 15:00:00'),

-- Catering Services Agreement
('07777777-7777-7777-7777-777777777771', 'f2346c13-6bd0-4fad-91a2-4def8fde03f4', NULL, 'DRAFT', 'David Miller', '2026-03-01 10:00:00'),
('07777777-7777-7777-7777-777777777772', 'f2346c13-6bd0-4fad-91a2-4def8fde03f4', 'DRAFT', 'REVIEW', 'David Miller', '2026-03-05 11:30:00'),
('07777777-7777-7777-7777-777777777773', 'f2346c13-6bd0-4fad-91a2-4def8fde03f4', 'REVIEW', 'APPROVED', 'VP Operations', '2026-03-10 14:00:00'),
('07777777-7777-7777-7777-777777777774', 'f2346c13-6bd0-4fad-91a2-4def8fde03f4', 'APPROVED', 'TERMINATED', 'David Miller', '2026-03-25 17:00:00'),

-- AWS Contract
('18888888-8888-8888-8888-888888888881', '01237d24-7ce1-4cbf-a2b3-5ef90fef04f5', NULL, 'DRAFT', 'Bob Johnson', '2026-06-02 11:00:00'),
('18888888-8888-8888-8888-888888888882', '01237d24-7ce1-4cbf-a2b3-5ef90fef04f5', 'DRAFT', 'REVIEW', 'Bob Johnson', '2026-06-04 15:30:00'),

-- Graphic Design
('29999999-9999-9999-9999-999999999991', '12348e35-8df2-4d00-b3c4-6f0a1f0f05f6', NULL, 'DRAFT', 'Charlie Brown', '2026-06-05 16:00:00'),

-- PartnerWeb Contract
('3aaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '23459f46-9e03-4e11-b4d5-7f1b2f1f06f7', NULL, 'DRAFT', 'David Miller', '2026-06-04 09:30:00'),
('3aaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab', '23459f46-9e03-4e11-b4d5-7f1b2f1f06f7', 'DRAFT', 'REVIEW', 'David Miller', '2026-06-06 14:00:00'),

-- SecOps Contract
('4bbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '34560a57-0f14-4f22-c5e6-8f2c3f2f07f8', NULL, 'DRAFT', 'Bob Johnson', '2026-05-02 10:00:00'),
('4bbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbc', '34560a57-0f14-4f22-c5e6-8f2c3f2f07f8', 'DRAFT', 'REVIEW', 'Bob Johnson', '2026-05-05 12:00:00'),
('4bbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbd', '34560a57-0f14-4f22-c5e6-8f2c3f2f07f8', 'REVIEW', 'APPROVED', 'VP IT Security', '2026-05-10 11:20:00'),

-- PatentHoldings Contract
('5ccccccc-cccc-cccc-cccc-cccccccccccc', '45671b68-1f25-4f33-d6f7-9f3d4f3f08f9', NULL, 'DRAFT', 'Alice Smith', '2026-04-15 13:00:00'),
('5ccccccc-cccc-cccc-cccc-cccccccccccd', '45671b68-1f25-4f33-d6f7-9f3d4f3f08f9', 'DRAFT', 'REVIEW', 'Alice Smith', '2026-04-18 10:00:00'),
('5ccccccc-cccc-cccc-cccc-ccccccccccce', '45671b68-1f25-4f33-d6f7-9f3d4f3f08f9', 'REVIEW', 'REJECTED', 'Legal Director', '2026-04-22 09:15:00');
