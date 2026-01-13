insert into org_edw.vChefOrganisationer (ChefOrganisationer_SK, PersonId, Login, OrgList) 
values
  -- Manager with responsibility for multiple organizations
  (1, '57B17CE9-F1EE-49C4-ABCD-1234567890AB', 'user01','9937|1281|1302'),

  -- Manager responsible for a single organization
  (2, 'A12F98C4-8B42-4F3A-9F12-ABCDEF123456', 'user02', '9936'),

  -- Manager responsible for several organizations, no overlap with 9936
  (3, 'C88E1234-55AA-4C9E-BBBB-9876543210FF', 'user03', '1401|1402|1403'),

  -- Manager with overlapping responsibility (useful for orgId searches)
  (4, 'D99F0001-AAAA-4BBB-CCCC-000000000001', 'user04', '1280|1401');
