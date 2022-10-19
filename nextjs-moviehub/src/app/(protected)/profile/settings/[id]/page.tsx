import React from 'react';

type PageProps = {
	params: {
		id: string;
	};
};

const Page: React.FC<PageProps> = ({ params }) => (
	<div>Settings for user ID: {params.id}</div>
);

export default Page;
