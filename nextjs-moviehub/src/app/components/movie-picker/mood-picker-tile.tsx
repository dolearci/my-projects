import Image from 'next/image';

import MoodButton from '../controls/mood-selection-button';

import MinusTile from './minus-tile';

type MoodPickerProps = {
	moods: Record<string, unknown>;
	removeTile: () => void;
	handleMoodSelection: (mood: string) => void;
};

const MoodPickerTile: React.FC<MoodPickerProps> = ({
	moods,
	removeTile,
	handleMoodSelection
}) => (
	<div className="animation-grow flex max-w-[350px] f-full mx-2 rounded-lg shadow-md hover:shadow-xl justify-center items-center transition-shadow ease-in duration-100">
		<div className="m-5 w-[350px] ">
			<div className="flex w-full">
				<div className="w-1/2 flex justify-start items-center">
					<Image
						className="pl-2 opacity-50"
						src="/mood-picker-user.svg"
						width={75}
						height={150}
						alt="mood-picker-user"
					/>
				</div>
				<div className="w-1/2 flex justify-end items-center">
					<MinusTile removeTile={removeTile} />
				</div>
			</div>
			<h1 className="text-mh-theme w-full text-left font-headings text-xl tracking-wider pl-2 pt-6 pb-2 font-bold">
				Pick your mood.
			</h1>

			<div className="flex-row justify-between">
				{Object.keys(moods).map((mood: string, index: number) => (
					<div key={index}>
						<MoodButton
							index={index}
							mood={mood}
							handleMoodSelection={handleMoodSelection}
						/>
					</div>
				))}
			</div>
		</div>
	</div>
);

export default MoodPickerTile;
