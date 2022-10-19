import React from 'react';

const Loading: React.FC = () => (
	<div className="fixed top-20 left-0 w-full h-1">
		<div className="h-full bg-blue-500 animate-loading" />
	</div>
);

export default Loading;
