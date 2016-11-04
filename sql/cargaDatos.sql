INSERT INTO public.user_type (id, description, version) VALUES (1, 'Joomla', 1);
INSERT INTO public.user_type (id, description, version) VALUES (4, 'Facebook', 1);
INSERT INTO public.user_type (id, description, version) VALUES (3, 'Usuario de correo', 1);
INSERT INTO public.user_type (id, description, version) VALUES (2, 'Administrador BBDD', 1);
INSERT INTO public.user_type (id, description, version) VALUES (5, 'Twitter', 1);
INSERT INTO public.user_type (id, description, version) VALUES (6, 'Google +', 1);


INSERT INTO public.contact_info_type(id, value, not_valid_message, regular_expression, version) values (1, 'Movil', '', '',1);
INSERT INTO public.contact_info_type(id, value, not_valid_message, regular_expression, version) values (2, 'Fijo', '', '',1);
INSERT INTO public.contact_info_type(id, value, not_valid_message, regular_expression, version) values (3, 'Fax', '', '',1);
INSERT INTO public.contact_info_type(id, value, not_valid_message, regular_expression, version) values (4, 'Email', '', '',1);

INSERT INTO public.RATE(id, description, key, value, version) VALUES (1, 'Coste por pagina', 'CPP', 100, 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (2, 'Coste por layout', 'CPL', 100, 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (3, 'Coste diseño logo', 'LOGO', 50  , 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (4, 'Integración redes sociales', 'RRSS', 100  , 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (5, 'Hosting', 'HOSTING', 20  , 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (6, 'Dominio', 'DOMAIN', 1  , 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (7, 'Mailing', 'MAILING', 10  , 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (8, 'Complejidad basica', 'BASIC_COMPLEX', 1  , 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (9, 'Complejidad media', 'MEDIUM_COMPLEX', 1.25 , 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (10, 'Complejidad avanzada', 'ADVANCED_COMPLEX', 1.50 , 1);
INSERT INTO public.RATE(id, description, key, value, version) VALUES (11, 'Actualizaciones', 'UPDATES', 30 , 1);