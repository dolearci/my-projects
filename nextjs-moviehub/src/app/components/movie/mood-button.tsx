import React from 'react';
import clsx from 'clsx';

import Button from '../controls/button';

type MoodButtonProps = {
	backgroundColor: string;
	label: string;
	className?: string;
	disabled?: boolean;
	onClick?: () => void;
};

const MoodButton: React.FC<MoodButtonProps> = ({
	backgroundColor,
	label,
	className,
	disabled = false,
	onClick
}) => (
	<Button
		disabled={disabled}
		className={clsx('', className, {
			'cursor-not-allowed opacity-50': disabled
		})}
		style={{ backgroundColor }}
		onClick={onClick}
	>
		{label}
	</Button>
);

export default MoodButton;
