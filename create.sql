create table companies (id  bigserial not null, balance numeric(19, 2) default 0.00, primary key (id));
create table employees (id  bigserial not null, company_id int8, primary key (id));
create table transactions (id  bigserial not null, amount numeric(19, 2) not null, created_at timestamp not null, deposit_type varchar(255) not null, modified_at timestamp not null, company_id int8, employee_id int8, primary key (id));
alter table if exists employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table if exists transactions add constraint FKeyusbb454wshg7tc93rvfkvte foreign key (company_id) references companies;
alter table if exists transactions add constraint FK3sipv6ebe2jvi2k7dvw8embx1 foreign key (employee_id) references employees;
create table companies (id  bigserial not null, created_at timestamp not null, balance numeric(19, 2) default 0.00, primary key (id));
create table employees (id  bigserial not null, created_at timestamp not null, company_id int8, primary key (id));
create table payments (id  bigserial not null, created_at timestamp not null, amount numeric(19, 2) not null, deposit_type varchar(255) not null, company_id int8, employee_id int8, primary key (id));
alter table if exists employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table if exists payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table if exists payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id  bigserial not null, created_at timestamp not null, balance numeric(19, 2) default 0.00, primary key (id));
create table employees (id  bigserial not null, created_at timestamp not null, company_id int8, primary key (id));
create table payments (id  bigserial not null, created_at timestamp not null, amount numeric(19, 2) not null, deposit_type varchar(255) not null, company_id int8, employee_id int8, primary key (id));
alter table if exists employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table if exists payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table if exists payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id  bigserial not null, created_at timestamp not null, balance numeric(19, 2) default 0.00, primary key (id));
create table employees (id  bigserial not null, created_at timestamp not null, company_id int8, primary key (id));
create table payments (id  bigserial not null, created_at timestamp not null, amount numeric(19, 2) not null, deposit_type varchar(255) not null, company_id int8, employee_id int8, primary key (id));
alter table if exists employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table if exists payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table if exists payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id bigint generated by default as identity, created_at timestamp not null, balance decimal(19,2) default 0.00, primary key (id));
create table employees (id bigint generated by default as identity, created_at timestamp not null, company_id bigint, primary key (id));
create table payments (id bigint generated by default as identity, created_at timestamp not null, amount decimal(19,2) not null, deposit_type varchar(255) not null, company_id bigint, employee_id bigint, primary key (id));
alter table employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id bigint generated by default as identity, created_at timestamp not null, balance decimal(19,2) default 0.00, primary key (id));
create table employees (id bigint generated by default as identity, created_at timestamp not null, company_id bigint, primary key (id));
create table payments (id bigint generated by default as identity, created_at timestamp not null, amount decimal(19,2) not null, deposit_type varchar(255) not null, company_id bigint, employee_id bigint, primary key (id));
alter table employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id bigint generated by default as identity, created_at timestamp not null, balance decimal(19,2) default 0.00, primary key (id));
create table employees (id bigint generated by default as identity, created_at timestamp not null, company_id bigint, primary key (id));
create table payments (id bigint generated by default as identity, created_at timestamp not null, amount decimal(19,2) not null, deposit_type varchar(255) not null, company_id bigint, employee_id bigint, primary key (id));
alter table employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id bigint generated by default as identity, created_at timestamp not null, balance decimal(19,2) default 0.00, primary key (id));
create table employees (id bigint generated by default as identity, created_at timestamp not null, company_id bigint, primary key (id));
create table payments (id bigint generated by default as identity, created_at timestamp not null, amount decimal(19,2) not null, deposit_type varchar(255) not null, company_id bigint, employee_id bigint, primary key (id));
alter table employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id bigint generated by default as identity, created_at timestamp not null, balance decimal(19,2) default 0.00, primary key (id));
create table employees (id bigint generated by default as identity, created_at timestamp not null, company_id bigint, primary key (id));
create table payments (id bigint generated by default as identity, created_at timestamp not null, amount decimal(19,2) not null, deposit_type varchar(255) not null, company_id bigint, employee_id bigint, primary key (id));
alter table employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id bigint generated by default as identity, created_at timestamp not null, balance decimal(19,2) default 0.00, primary key (id));
create table employees (id bigint generated by default as identity, created_at timestamp not null, company_id bigint, primary key (id));
create table payments (id bigint generated by default as identity, created_at timestamp not null, amount decimal(19,2) not null, deposit_type varchar(255) not null, company_id bigint, employee_id bigint, primary key (id));
alter table employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id bigint generated by default as identity, created_at timestamp not null, balance decimal(19,2) default 0.00, primary key (id));
create table employees (id bigint generated by default as identity, created_at timestamp not null, company_id bigint, primary key (id));
create table payments (id bigint generated by default as identity, created_at timestamp not null, amount decimal(19,2) not null, deposit_type varchar(255) not null, company_id bigint, employee_id bigint, primary key (id));
alter table employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
create table companies (id bigint generated by default as identity, created_at timestamp not null, balance decimal(19,2) default 0.00, primary key (id));
create table employees (id bigint generated by default as identity, created_at timestamp not null, company_id bigint, primary key (id));
create table payments (id bigint generated by default as identity, created_at timestamp not null, amount decimal(19,2) not null, deposit_type varchar(255) not null, company_id bigint, employee_id bigint, primary key (id));
alter table employees add constraint FK1ekpcbo0lmdx6ou8e3fh9j4lq foreign key (company_id) references companies;
alter table payments add constraint FKd7gx3doh12b2qx2b9j2e1dsxe foreign key (company_id) references companies;
alter table payments add constraint FKgg9970yjb56tmui83b0dccqv5 foreign key (employee_id) references employees;
