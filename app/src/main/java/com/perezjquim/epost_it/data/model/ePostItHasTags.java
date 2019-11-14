package com.perezjquim.epost_it.data.model;

import com.orm.*;
import java.sql.*;

public class ePostItHasTags extends SugarRecord
{
        private Long id;

        private ePostIt epost_it;
        private Tag tag;

        public ePostItHasTags() {}

        public ePostItHasTags(int epost_it, int tag)
        {
                this.epost_it = epost_it;
                this.tag = tag;
        }
}
