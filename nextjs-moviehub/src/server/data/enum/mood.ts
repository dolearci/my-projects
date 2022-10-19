import Genre from '@/server/data/enum/genre';

const Mood = {
	Intense: [
		Genre.Action,
		Genre.Adventure,
		Genre.Horror,
		Genre.Thriller,
		Genre.Crime,
		Genre.ScienceFiction,
		Genre.War
	],
	Heartwarming: [
		Genre.Animation,
		Genre.Family,
		Genre.Comedy,
		Genre.Romance,
		Genre.Drama
	],
	Thoughtful: [Genre.Documentary, Genre.History, Genre.Mystery],
	Energetic: [Genre.Music, Genre.Fantasy, Genre.Western, Genre.TVMovie]
};

export default Mood;
