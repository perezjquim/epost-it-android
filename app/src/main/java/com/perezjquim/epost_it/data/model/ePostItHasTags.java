package com.perezjquim.epost_it.data.model;

import com.orm.*;
import java.sql.*;

public class ePostItHasTags extends SugarRecord
{
        private int id_epostit;
        private int id_tag;

        public ePostItHasTags() {}

        public ePostItHasTags(int id_epostit, int id_tag)
        {
                this.id_epostit = id_epostit;
                this.id_tag = id_tag;
        }
}
