drop database if exists CL3_QuispeAldazabal;
create database CL3_QuispeAldazabal;
use CL3_QuispeAldazabal;

create table Producto(
id_prd int primary key auto_increment,
nom_prd varchar(100),
des_prd varchar(100),
fch_prd date not null default(now())
);
select * from Producto;