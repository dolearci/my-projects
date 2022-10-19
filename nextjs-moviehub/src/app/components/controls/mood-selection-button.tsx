'use-client';
import React, { useState } from 'react';

import Button from '@/app/components/controls/button';

type MoodButtonProps = {
	mood: string;
	index: number;
	handleMoodSelection: (mood: string) => void;
};

const MoodButton: React.FC<MoodButtonProps> = ({
	mood,
	index,
	handleMoodSelection
}) => {
	const [isClicked, setIsClicked] = useState(false);

	return (
		<Button
			className={`w-[96%] m-2 flex-grow font-weight-[500] ${isClicked ? 'bg-gray-300' : 'hover:bg-gray-200'}`}
			key={index}
			onClick={() => {
				setIsClicked(!isClicked);
				handleMoodSelection(mood);
			}}
		>
			{mood}
		</Button>
	);
};

export default MoodButton;
