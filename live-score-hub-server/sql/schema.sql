drop database if exists live_score_hub;
create database live_score_hub;
use live_score_hub;

create table app_user (
	app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    email varchar(255) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default 1
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

create table team (
	team_id int primary key auto_increment,
    team_name varchar(100) not null,
    league varchar(25) not null,
    abbreviation varchar(10) not null,
    logo_url varchar(255)
);

create table game (
	game_id int primary key auto_increment,
    home_id int not null,
    away_id int not null,
    game_date datetime not null,
    home_score int default 0,
    away_score int default 0,
	constraint fk_game_home_id
		foreign key(home_id)
        references team(team_id),
	constraint fk_game_away_id
		foreign key(away_id)
        references team(team_id)
);

create table notification (
	notification_id int primary key auto_increment,
    user_id int not null,
    game_id int not null,
    notification_type ENUM("PRE-GAME", "QUARTER_START", "QUARTER_END", "GAME_END") not null,
    notifcation_time datetime not null,
    constraint fk_notification_user_id
		foreign key(user_id)
        references app_user(app_user_id),
	constraint fk_notification_game_id
		foreign key(game_id)
        references game(game_id)
);
    
    
