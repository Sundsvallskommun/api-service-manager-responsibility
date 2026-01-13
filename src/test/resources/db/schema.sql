drop table if exists org_edw.vChefOrganisationer;
drop schema if exists org_edw;
create schema org_edw;
create table org_edw.vChefOrganisationer (
    ChefOrganisationer_SK BIGINT NOT NULL PRIMARY KEY,
    PersonId UNIQUEIDENTIFIER NOT NULL,
    Login NVARCHAR(100) NOT NULL,
    OrgList NVARCHAR(2000) NULL
);