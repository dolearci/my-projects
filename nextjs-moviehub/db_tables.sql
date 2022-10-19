CREATE TABLE User
  (
      UserId   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
      Username TEXT NOT NULL UNIQUE,
      Password TEXT NOT NULL,
      Email    TEXT,
      IsAdmin  INTEGER DEFAULT 0
  );

INSERT INTO User (Username, Password, Email, IsAdmin)
VALUES ('john_doe', 'my_secure_password', 'john.doe@example.com', 0);

CREATE TABLE Movie
(
    MovieId INTEGER PRIMARY KEY,
    Title TEXT NOT NULL
);

CREATE TABLE MovieList
(
    ListId    INTEGER PRIMARY KEY AUTOINCREMENT,
    ListName  TEXT    NOT NULL,
    CreatorId INTEGER NOT NULL,
    FOREIGN KEY (CreatorId) REFERENCES User (UserId) ON DELETE SET NULL
);

CREATE TABLE MovieListMovie
(
    MovieListId INTEGER NOT NULL,
    MovieId     INTEGER NOT NULL,
    PRIMARY KEY (MovieListId, MovieId),
    FOREIGN KEY (MovieListId) REFERENCES MovieList (ListId),
    FOREIGN KEY (MovieId) REFERENCES Movie (MovieId)
);

-- Table for storing user's favorite lists
CREATE TABLE FavoriteList
(
    FavoriteId INTEGER PRIMARY KEY AUTOINCREMENT,
    UserId     INTEGER NOT NULL,
    ListId     INTEGER NOT NULL,
    FOREIGN KEY (UserId) REFERENCES User (UserId) ON DELETE CASCADE,
    FOREIGN KEY (ListId) REFERENCES MovieList (ListId) ON DELETE CASCADE,
    UNIQUE (UserId, ListId) ON CONFLICT IGNORE
);
