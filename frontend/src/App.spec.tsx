/**
 * @jest-environment jsdom
 */
import App from './App'
import {render, screen, fireEvent} from '@testing-library/react'
// note: you can make these globally available from vite.config.js
// see the Vitest docs for more: https://vitest.dev/config/#globals
import {describe, expect, it} from 'vitest'
import {act} from "react";

describe('App', () => {
    describe("Layout", () => {
        it("should display a count of 0 after mount", () => {
            render(<App/>)
            expect(screen.queryByText(/count is 0/i)).toBeInTheDocument()
        })
    })

    describe("Interactions", () => {
        it("should increase by one when button is clicked", async () => {
            render(<App/>)
            const countButton = screen.getByRole("button")

            await act(async () => fireEvent.click(countButton))
            expect(screen.queryByText(/count is 1/i)).toBeInTheDocument()
        })
    })
})