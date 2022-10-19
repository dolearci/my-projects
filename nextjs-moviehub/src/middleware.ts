export { default } from 'next-auth/middleware';

export const config = {
	matcher: [
		// user	pages
		'/profile/:path*',
		'/movie-lists/:path*',

		// admin pages
		'/movie-list-management/:path*',
		'/user-management/:path*'
	]
};
