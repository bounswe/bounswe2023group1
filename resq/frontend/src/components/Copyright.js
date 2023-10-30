import Typography from "@mui/material/Typography";
import Link from "@mui/material/Link";
import * as React from "react";

export function Copyright(props) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <Link color="inherit" href="https://github.com/bounswe/bounswe2023group1">
                RESQ
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}