'use client';

import { SessionProvider } from 'next-auth/react';
import { type PropsWithChildren } from 'react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

export const queryClient = new QueryClient();

export const Providers = ({ children }: PropsWithChildren) => (
	<SessionProvider>
		<QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
	</SessionProvider>
);
