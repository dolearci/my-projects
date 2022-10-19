'use client';

import { useRef, useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { toast } from 'react-toastify';
import Link from 'next/link';

import MoviePickerTile from '@/app/components/movie-picker/movie-picker-tile';
import Button from '@/app/components/controls/button';
import Loading from '@/app/loading';
import MovieTile from '@/app/components/movie/movie-tile';
import MoodPickerTile from '@/app/components/movie-picker/mood-picker-tile';
import PlusTile from '@/app/components/movie-picker/plus-tile';
import Mood from '@/server/data/enum/mood';
import { type MovieDto } from '@/server/data/dto/movie-dto';
import MoviePicker from '@/server/data/util/movie-picker';

type SelectedMoods = {
	selectedMoods: Array<keyof typeof Mood>;
};

export type SelectedButtons = {
	selectedButtons: Record<number, Array<keyof typeof Mood>>;
};

const maxNumberOfPicks = 4;

const Page = () => {
	const movies = useRef<MovieDto[]>([]);
	const moods = Mood;

	const [numberOfPicks, setNumberOfPicks] = useState(1);

	const mutation = useMutation<MovieDto[]>({
		mutationKey: ['movies'],
		mutationFn: () =>
			MoviePicker.fetchMoviesForMoods(selectedOption.selectedMoods, true)
	});

	const randomMoviesMutation = useMutation<MovieDto[]>({
		mutationKey: ['get-random-movies'],
		mutationFn: () =>
			MoviePicker.getMoviesForMood(selectedOption.selectedMoods, 4)
	});

	const [pickerState, setPickerState] = useState({
		isPickerOpened: false,
		areMoviesChosen: false
	});

	const [selectedOption, setSelectedOption] = useState<SelectedMoods>({
		selectedMoods: []
	});

	const onSubmit = () => {
		if (selectedOption.selectedMoods.length === 0) {
			setSelectedOption({
				selectedMoods: Object.keys(Mood) as Array<keyof typeof Mood>
			});
		}

		mutation.mutate(undefined, {
			onSuccess: (fetchedMovies: MovieDto[]) => {
				movies.current = fetchedMovies;
				toast.success('Picking movie for you !');

				if (!movies) {
					throw new Error('Session is null.');
				}

				//setOpenPicker(true);
				setPickerState(prevState => ({ ...prevState, isPickerOpened: true }));
			},
			onError: () => {
				toast.error('Login failed.');
			}
		});

		setTimeout(() => {
			randomMoviesMutation.mutate(undefined, {
				onSuccess: (fetchedMovies: MovieDto[]) => {
					movies.current = fetchedMovies;
					setPickerState({ isPickerOpened: false, areMoviesChosen: true });
					toast.success('We picked film for you!');
				}
			});
		}, 6000);
	};

	const handleMoodSelection = (mood: string) => {
		if (!selectedOption.selectedMoods.includes(mood as keyof typeof Mood)) {
			setSelectedOption(prevOption => ({
				selectedMoods: [...prevOption.selectedMoods, mood as keyof typeof Mood]
			}));
		}
	};

	const handleReset = () => {
		setPickerState({
			isPickerOpened: false,
			areMoviesChosen: false
		});
		setSelectedOption({ selectedMoods: [] });
	};

	const handleAddingMoodPickerTile = () => {
		console.log('Adding mood picker tile', numberOfPicks);
		setNumberOfPicks(prevState =>
			prevState === maxNumberOfPicks ? maxNumberOfPicks - 1 : prevState + 1
		);
	};

	const handleRemovingMoodPickerTile = () => {
		setNumberOfPicks(prevState => (prevState === 0 ? 1 : prevState - 1));
	};

	return (
		<div className="items-centre w-full transition ease-in-out duration-100 p-6">
			{mutation.isPending && <Loading />}

			<h1 className="text-mh-theme text-2xl font-bold mb-6 font-headings">
				Movie picker
			</h1>
			{!pickerState.isPickerOpened && !pickerState.areMoviesChosen && (
				<div className="flex-row justify-center my-10">
					<div className="grid lg:gap-2 lg:gap-y-2 grid-cols-1 lg:grid-cols-2 mt-10">
						{Array.from({ length: numberOfPicks + 1 }).map((_, index) => (
							<div
								className={`w-full h-full justify-center flex items-center ${index % 2 === 1 ? 'xl:justify-start' : 'xl:justify-end'}`}
								key={index}
							>
								{index === numberOfPicks && index !== maxNumberOfPicks ? (
									<PlusTile addTile={handleAddingMoodPickerTile} />
								) : (
									<>
										{index !== numberOfPicks && (
											<MoodPickerTile
												moods={moods}
												removeTile={handleRemovingMoodPickerTile}
												handleMoodSelection={handleMoodSelection}
											/>
										)}
										{}
									</>
								)}
							</div>
						))}
					</div>

					<div className="w-full flex justify-center my-10">
						<Button
							className="w-[600px] m-2 hover:bg-gray-200"
							onClick={() => {
								onSubmit();
							}}
						>
							Pick movies
						</Button>
					</div>
				</div>
			)}

			{pickerState.isPickerOpened && (
				<div className="animate-fadeIn overflow-hidden w-full h-[800px] rounded-xl transition-transform ease-in-out duration-300">
					<div className="flex space-x-4 animate-marquee w-full py-4">
						{movies.current.map(movie => (
							<div key={movie.id} className="shadow-sm">
								<div className="p2">
									<MoviePickerTile
										imageUrl={movie.poster_path}
										title={movie.title}
									/>
								</div>
							</div>
						))}
					</div>
				</div>
			)}

			{pickerState.areMoviesChosen && (
				<>
					<div className="animate-fadeIn grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-4">
						{movies.current.map((movie, index) => (
							<Link key={index} href={`/movie-detail/${movie.id}`}>
								<MovieTile
									imageUrl={movie.poster_path}
									title={movie.title}
									year={movie.release_date.split('-')[0]}
									rating={movie.vote_average.toPrecision(2)}
									description={movie.overview}
									genres={movie.genre_ids}
								/>
							</Link>
						))}
					</div>
					<div className="w-full flex justify-center my-10">
						<Button
							className="w-[600px] m-2 hover:bg-gray-200"
							onClick={() => {
								handleReset();
							}}
						>
							Back to mood selection
						</Button>
					</div>
				</>
			)}
		</div>
	);
};

export default Page;
