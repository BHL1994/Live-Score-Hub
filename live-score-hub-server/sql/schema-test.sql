drop database if exists live_score_hub_test;
create database live_score_hub_test;
use live_score_hub_test;

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
    `name` varchar(100) not null,
    city varchar(100) not null,
    team varchar(100) not null,
    league varchar(25) not null,
    abbreviation varchar(10) not null,
    logo_url varchar(255)
);

create table game (
	game_id int primary key,
    home_id int not null,
    away_id int not null,
    game_date datetime not null,
    game_status varchar(50),
    period int,
    league varchar(25) not null,
    time_remaining varchar(10),
    home_score int default 0,
    away_score int default 0,
	home_period_scores varchar(100),
    away_period_scores varchar(100),
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
    game_id int not null unique,
    notification_type ENUM("PRE_GAME", "QUARTER_START", "QUARTER_END", "GAME_END") not null,
    notification_time datetime not null,
    constraint fk_notification_user_id
		foreign key(user_id)
        references app_user(app_user_id),
	constraint fk_notification_game_id
		foreign key(game_id)
        references game(game_id)
);

delimiter //

create procedure set_known_good_state()
begin

	delete from app_user_role;
	delete from app_user;
	alter table app_user auto_increment = 1;
	delete from app_role;
	alter table app_role auto_increment = 1;
    delete from notification;
    alter table notification auto_increment = 1;
    delete from game;
    alter table game auto_increment = 1;
    delete from team;
    alter table team auto_increment = 1;

	insert into app_role (`name`) values
		('USER'),
		('ADMIN');
    
	insert into app_user (username, email, password_hash, enabled)
	values
		("JSmith", 'john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
		("SJones", 'sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

	insert into app_user_role
		values
		(1, 2),
		(2, 1);

    INSERT INTO team (name, city, team, league, abbreviation, logo_url) VALUES
        ('Chicago White Sox', 'Chicago', 'White Sox', 'MLB', 'CWS', 'https://a.espncdn.com/i/teamlogos/mlb/500/chw.png'),
        ('Detroit Tigers', 'Detroit', 'Tigers', 'MLB', 'DET', 'https://a.espncdn.com/i/teamlogos/mlb/500/det.png'),
        ('Kansas City Royals', 'Kansas City', 'Royals', 'MLB', 'KC', 'https://a.espncdn.com/i/teamlogos/mlb/500/kc.png');
        
end //

delimiter ;


    
