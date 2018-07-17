drop database if exists projet;

create database projet;

\c projet;

create table Personnel (
personnel_id serial primary key,
Nom varchar(20) not null,
Prenom varchar(20) not null,
Email varchar(50) not null unique,
Metier varchar(20) not null unique);

create table RH (
Pseudo varchar(20) not null unique,
MotDePasse varchar(20) not null unique) inherits(Personnel);

create table Questionnaire (
questionnaire_id serial primary key,
Titre varchar(255) not null,
DateCreation timestamp not null,
DateLimite timestamp not null,
IntervalleRelance int not null);

create table QuestionnairePersonnel (
questionnaire_id int not null references Questionnaire(questionnaire_id),
personnel_id int not null references Personne(personnel_id));

create table Liste (
liste_id serial primary key,
Titre varchar(255) not null);

create table ListePersonnel (
liste_id int not null references Liste(liste_id),
personnel_id int not null references Personnel(personnel_id));

create table Question (
question_id serial primary key,
questionnaire_id int not null references Questionnaire(questionnaire_id),
Libelle varchar(255) not null);

create table QuestionTexte (
NbColonnesZoneTexte int not null,
NbLignesZoneTexte int not null) inherits(Question);

create table QuestionChoix (
PlusieursChoix boolean not null) inherits(Question);

create table Choix (
question_id int not null references Question(question_id),
Libelle varchar(255) not null);

create table Reponse (
question_id int not null references Question(question_id),
personnel_id int not null references Personnel(personnel_id),
contenuReponse text not null);

insert into RH values
(1, 'Bréhu', 'Soraya', 'soraya.brehu@gmail.com', 'RH', 'linda', '1234'),
(2, 'Ratodiarivony', 'Michaëlle', 'michaelle.ratodi@gmail.com', 'RH', 'michaelle', 'michaelle'),
(3, 'Themelin', 'Mathieu', 'mat.themelin@hotmail.fr', 'RH', 'mathieu', 'mathieu');

insert into Personnel values 
(1, 'Hugo' ,'LLORIS','hugo@gmail.com', 'RH'),
(2, 'Benjamin','PAVARD', 'berjamin@gmail.com', 'IT manager'),
(3, 'Lucas','HERNANDEZ', 'lucas.tata@gmail.com', 'employee1'),
(4, 'Steve' ,'MANDANDA', 'steve.tata@gmail.com', 'employee2'),
(5, 'Benjamin','MENDY', 'b2@gmail.com', 'employee3'),
(6, 'Samuel','UMTITI', 's1.tata@gmail.com', 'employee4'),
(7, 'Adil','RAMI', 'a.r@gmail.com', 'employee5'),
(8, 'Olivier','GIROUD', 'o.g.tata@gmail.com', 'employee6'),
(9, 'Nabil','FEKIR', 'n.f@gmail.com', 'employee7'),
(10, 'Steven','NZONZI', 's.n@gmail.com', 'employee8');